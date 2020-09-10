# Notes 
# C 
gcc filename.extension
cc filename.extension

- file name.out
- ldd name.out
- objdump -d a.out | less
    - display machine code 
- ELF loaded into memory, executed by processor
- echo $PATH
- ./executable
- man 3 printf
- cc printf.c -o printf.out
```c
int main(int argc, char *argv[]) 
{
    for (int 0 = 0; i < argc; i++) {
        printf("%s\n", argv[i]);
    }
}
```
```c
#include <stdio.h>  //preprocessor handles this

//compiler handles this
//loader starts this program
int main(void) 
{
    const char *str = "Hello, world!; //\0
    //float, decimal, string
    printf("%f %d %s\n", str, str, str);

    int inputRead = scanf("str_input", "format", &vars, &vars) 

    //%p pointer, %c character

}
```

## Unix and Bourne Shell
- cat files 
    - prints files out
- cd directory
- cp OLD-NAME NEW-NAME
- echo input
- ls directory
    - lists directory
- mkdir NEW-NAME
- mv OLD-NAME NEW-NAME
- pwd 
    - print working directory
- rm FILE-TO-REMOVE
- rmdir DIR-TO-REMOVE
- man command
## vi
- :q  to quit
- :save

## git
- git config --global user.name "FULL NAME"
- git config --global user.email EMAIL
- git add files (. for all)
- git commit -m "Describe change"
    - -s, records person responsible
- git log
    - git log -p
- git diff before git add

## What is software engineering?
- 