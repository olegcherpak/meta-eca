/*
 * Edison PWR button handler
 *
 * Copyright (c) 2014, Intel Corporation.
 * Fabien Chereau <fabien.chereau@intel.com>
 * Loïc Akue <loicx.akue@intel.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */


#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <assert.h>
#include <sys/poll.h>
#include <fcntl.h>
#include <time.h>
#include <sys/ioctl.h>
#include <sys/types.h>
#include <linux/input.h>

/* See full definitions in include/linux/input.h */
/* Also find more doc in Documentation/input/input.txt */

/*
 * Edison Arduino or breakout board PWR button code
 * EV_KEY KEY_POWER
 * Helmut Tschemernjak: 4-Dec-14
 * - Added 3 click shutdown support (click within four seconds)
 * - Optmized code to use stderr which gets logged via systemd
 * - Build non debug versions via:
 *    cc -O -DNDEBUG pwr-button-handler.c -o pwr_button_handler
 */

/* We use 2 seconds for now */
#define EDISON_OOBE_PRESS_TIMEOUT 2
/* We detect a shutdown we three power press within 3 seconds */
#define EDISON_SHUTDOWN_PRESS_TIMEOUT 4
#define EDISON_SHUTDOWN_PRESS_COUNT 3

#define CALLED_COMMAND_ON_TIMEOUT "/usr/bin/ap-mode-toggle toggle"
#define CALLED_COMMAND_ON_SHUTDOWN "/bin/systemctl poweroff"

#ifndef NDEBUG
char *getType(unsigned short type)
{
	char *s;

	switch(type) {
	case EV_KEY:
		s = "EV_KEY";
		break;
	case EV_REL:
		s = "EV_REL";
		break;
	case EV_ABS:
		s = "EV_ABS";
		break;
	default:
		s = "unknown"; /* check for other defines in input.h */
	}
	return s;
}

char *getCode(unsigned short code)
{
	char *s;

	switch(code) {
	case KEY_POWER:
		s = "KEY_POWER";
		break;
	default:
		s = "unknown"; /* check for other defines in input.h */
	}
	return s;
}
#endif


int main(int argc, char **argv)
{
    struct input_event event;
    struct pollfd p;
    int fd, n;
    ssize_t len;

    /* Time of the last press event.
       We reset this to zero when the button is released */
    time_t time_at_last_press = 0;
    time_t shutdown_press_start = 0;
	int shutdown_press_count = 0;

    fd = open("/dev/input/event1", O_RDONLY);
    if (fd < 0) {
        perror("Can't open /dev/input/event1 device");
        return fd;
    }

    memset(&p, 0, sizeof(p));
    p.fd = fd;
    p.events = POLLIN;

    while (1) {
        n = poll(&p, 1, -1);
        if (n < 0) {
            perror("Failed to poll /dev/input/event1 device");
            break;
        }

        if (n == 0)
            continue;

        len = read(fd, &event, sizeof(event));
        if (len < 0) {
            perror("Reading of /dev/input/event1 events failed");
            break;
        }

        if (len != sizeof(event)) {
            perror("Wrong size of input_event struct");
            break;
        }

        /* ignore non KEY event, and non RM button events */
        if (event.type != EV_KEY || event.code != KEY_POWER)
            continue;

#ifndef NDEBUG
	{
		time_t tt = event.time.tv_sec;
		struct tm tm1;

		localtime_r(&tt, &tm1);

		printf("%02d:%02d.%03d type=%s code=%s value=%u\n",
		       tm1.tm_min, tm1.tm_sec, event.time.tv_usec / 1000,
		       getType(event.type), getCode(event.code), event.value);
	}
#endif

        switch (event.value)
        {
            case 1: /* Regular press */
                assert(time_at_last_press==0);
                time_at_last_press = event.time.tv_sec;
		if (event.time.tv_sec - shutdown_press_start >= EDISON_SHUTDOWN_PRESS_TIMEOUT)
			shutdown_press_start = 0;
		if (!shutdown_press_start) {
			shutdown_press_start = event.time.tv_sec;
			shutdown_press_count = 0;
		}
                break;
            case 2: /* Auto repeat press */
                if (time_at_last_press == 0)
                {
                    /* This could happen if the user start pressing before the kernel starts */
                    time_at_last_press = event.time.tv_sec;
                }
                break;
            case 0: /* Release */
                if (event.time.tv_sec - time_at_last_press >= EDISON_OOBE_PRESS_TIMEOUT)
                {
                    time_at_last_press = 0;
                    fprintf(stderr,
			    "Edison PWR button was pressed more than %ds, "
			    "starting AP toggle service..\n",
			    EDISON_OOBE_PRESS_TIMEOUT);
                    system(CALLED_COMMAND_ON_TIMEOUT);
                }
                time_at_last_press = 0;

		if (event.time.tv_sec - shutdown_press_start <= EDISON_SHUTDOWN_PRESS_TIMEOUT) {
			if ((shutdown_press_count++ +1) == EDISON_SHUTDOWN_PRESS_COUNT) {
				fprintf(stderr,
					"Edison PWR button was pressed %d "
					"times within %ds, starting "
					"shutdown..\n",
					EDISON_SHUTDOWN_PRESS_COUNT,
					EDISON_SHUTDOWN_PRESS_TIMEOUT);
				shutdown_press_start = 0;
				shutdown_press_count = 0;
				system(CALLED_COMMAND_ON_SHUTDOWN);
			}
		} else {
			shutdown_press_start = 0;
			shutdown_press_count = 0;
		}
                break;
            default:
                fprintf(stderr,
			"Warning: unhandled PWR button event value: %u\n",
			event.value);
        }
    }

    close(fd);

    return 0;
}
