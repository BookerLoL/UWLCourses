# CS370 Notes

## Chapter 1
- API: Application Programmer's Interface
  - functions, semantics, return values, library-dependent
  
- ABI: Application Binary Interface
  - register conventions, stack frame format, memory sections, OS and machine-dependent
  
- ISA: Instruction Set Architecture 
  - instruction types, data sizes, instruction format, addressing modes, machine-dependent
  
- Processor: performs computation
- Memory: hold info for processor
- Input and Output: processor uses to communicate to outside world 
  
- Number Representation
  - Value: amt of sth
  - Notation: how to express the value
  - Encoding: meaning of value
    
- Decimal -> Binary
  - Powers of Two
    - subtract greatest power of 2 that is <= number
  - Repeated Division
    - divide number by two, keep track of remainder, repeat until at zero
    
- Hexadecimal Notation (0-F)
  - Every number in hexa is 4 bits in binary

- Two's complement (2C)
  - Highest bit position = sign bit
  
 - Decimal to 2C
  - normal Decimal -> Binary
  - if negative, invert all bits, add +1
  
- Binary Operations
  - Add: like normal addition, but carry over in left-most column, then throw away
  - Negate (2C only): invert all bits -> +1
  - Subtract: same as subtraction or if 2C then negate subtrahend and add to addend
  - Shift 
    - Shift-right: shift bits to right, check sign bit and replicate
    - Shift-left: shift bits to left, replace right bits with 0
   
   - NOT: invert bits
   - AND: if both 1, then 1, else 0,  NAND is NOT(AND)
   - OR / NOR: if 1 of them is 1, then 1 else 0.  NOR is NOT(OR)
   
   - Sign-extend: based on sign bit and if 2C then fill with upper-bit position value 
   
  
  
