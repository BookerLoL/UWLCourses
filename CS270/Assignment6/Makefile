#
# A simple makefile
#


CC = gcc
CFLAGS = -Wall
LDFLAGS =
OBJFILES = simulator.o gen_list.o support.o
TARGET = simulator

all: $(TARGET)

$(TARGET): $(OBJFILES)
	$(CC) $(CFLAGS) -o $(TARGET) $(OBJFILES) $(LDFLAGS)

clean:
	rm -f $(OBJFILES) $(TARGET) *~
