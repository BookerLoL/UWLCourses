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
   
---
## Chapter 2
    gcc -E <hll-src>

        -S <hll-src>

        -c <assmbly>

        <object file>

- PC indicate current exe instruction [incr by 4]
  - .data don't count

- RISC vs CISC 
  - RISC:
        - simple instrs, fixed bits, handful addressing modes, only operate on reg/immed vals
  - CISC:
        - operate on mem, variable length, many addressing modes

- Static Instr, from source code without run-time info
    - mnemomic, reg names, immed values, PC, addr of jump/branch
- Dynamic Instr, instr as they execute, with all info needed to determine outcome of prog

- Mips Format
    - R-type: 3 reg op / shift amt 
    - I-type: immed val
    - J-type: jump offset

- First instruction always at 0x00400000


- Two-Pass Assembler (scans code twice)
    - labels -> need to know target address

- Symbol Table
    - Label-Address pair

- program executable
    - file with all encoded instrs (seq order)
---
## Chapter 3
- Digital Logic Circuits 
    - voltage should be either 0 / 1

- Logically Complete
    - min set of logic funcs -> descr any other logic func
  
- logical functions 
    - use truth tables & logic equations
  
- Truth Table
    - all combinations on left, output on right
    - A B | AB

- Logic Eq Symbols
    - boolean 
        - black circle -> AND
        - '+' -> OR
        - '-' (bar on top) -> NOT
    
    diagrams
    ![logic](./img/logic_circuit_diagram.png)

    ![circuit_shortcuts](./img/circuit_diagram_shortcuts.png)

- Combinational logic: output depends on only curr set inputs

- Sequential logic: output depends on both inputs (& past)

- Sum Of Products
    - impl combinational logic 
    - AND gate to impl minterms with 1 in output
    
    - Minterms: 1 row of truth tables
    ![sop](./img/sop.png)

- Circuit performance
    - speed based on
        - critical-path delay: longest seq gates
        - wire delay: length of wire between gates
        - fan-out: avg # gates driven / gate
  
- Combinational Logic
    - Full Adder
        - input: A, B, Ci (prev carry bit)
        - Output: S (sum bit), Co (carry over)
        - S = A xor B xor Ci
        - Co = AB + BCi + ACi

    - Subtraction
        - negate subtrahend and add to addend 
        - use full adder

    - Ripple Carry Adder 
        - multiple full-adders that feed each other
            - most significant bit if carried over is overflow 
        - recursive  
      
      - Arithmetic Logic Unit 
          - ALU
          - select which function you want
          ![alu](./img/alu.png)

      - Multiplexer 
          - Mux, data input values -> single output "select"
          - always a single output
          - based on input bits to select operation
          ![multiplexer](./img/multiplexer.png)


  - Sequential Logic
      - RS Latch (most basic)
          - R 
          - S 
          - Q
          - Q (bar)
          - Create truth table for logic gates
                1)  use R and S values
                2) find Q and bar Q value
                3) save those values for next inputs
                4) use new input values and see if Q and bar Q remain the same.
                    - if Q and bar Q are the same there is a problem and don't use that 
      - Gated SR Latch
          - S, E, R
          - E if 1, enables latch Q
          - but there is a race condition 
      - Gated D Latch
          - Remove R, rename S to D, add a not gate to D 
          - 1 inputed gated latch
          - Q will reflect D as long as W is high
          ![dateddlatch](./img/gateddlatch.png)
      
      - D Flip-Flop
          - avoids races
          - signal transition twice
              - h -> l -> h
              - l -> h -> l
          - edge triggered, final Q only change on rising/falling edge
          ![dflipflop](./img/dflipflop.png)
          ![timingdiagram](./img/timingdiagram.png)
          - if aperiodic, need to add clock input to coordinate movement 
      - Clock
          - 1 bit signal that alternates as chip is powered 
          - heartbeat (most important)
          - cycle: complete transition from h->l->h
          - period (T) in seconds for 1 cycle
          - frequencey (f): # cycles / second, Hertz (Hz, 1/s)
          ![clock](./img/clock.png)
      - Multibit Register
          - register: multiple D flip-flops, all sharing same clock signal
          - saves multiple bits 
      - Random Access Memory (RAM)
          - rows: location / words
          - cols: bits for each word
          - each word: iden by addr
          - needs: one-hot decoder
          - read & write operations
          - inputs: 
              - (A) Address: (iden 1 loc)
                  - **bits: log2(# locations)**
              - (W) Write-enable: R / W
                  - **bits: 1**
              - (D) Data-in: 
                  - if W, D-input should have value
                  - else ignore
                  - **bits: by wordsize**
          - output
              - Data-out (D)
                  - if R, output prev saved data for spec loc
                  - **bits: by wordsize**
          - Write
              - set addr input to bin loc to write to
              - set input D value to write
              - set write-enable (W)to 1
               - **loc must be written before read, otherwise indeterminate**
          - Read
              - set addr input to loc to read
              - set write enable to 0
              - output Q read prev saved data val
          - ![readwritetable](./img/readwritetable.png)
          - ![memaccess](./img/memaccess.png)
          - One-Hot Decoder (Row Decoder)
              - n bits, addr
              - 2^n output (wordlines)
              - 1 output of 1, others will be 0
          - RAM with 32-bit words, total capacity of 1024 bytes, how many locations will RAM have?
          - ![shortcuts](./img/circuitshorthand.png)

        - Read/Write Ports
            - Read port
                - addr input & data-out output
            - Write port
                - addr input & data-in input (write enable)
            
            - allows simultaneous reads/write in cylce with ports
                - addr totally diff
                - new addr input 
                      - **one-hot decode** for write ports
                      - **muxes** for read ports
                      - no additional flip-flops
            - ![tworeadports](./img/tworeadport.png)
--- 
## Chapter 4 
- Single Cycle Mips Processor
- Processor Design
    - run prog, OS copy instr -> main mem loc
        - uses **loader** to do this
- Executing Instructions (High-level)
    - **Fetch** 
        - read op from main mem 
        - instr addr held in reg PC
    - **Decode**
        - grab opcode/funct 
    - **Register Read**
        - read register file RAM to get reg values
    - **Execute**
        - arithemetic: set select op input of ALU
        - load: **read mem**, addr calc (add calc)
        - store: **write mem**, need to do addr calc
        - branch: check cond then set PC
        - jump: change PC
                
    - **Write Back** 
        - Dest reg operands, vals written
            - load, arithmetic, rt/rd
        - PC written
            - if branc / jump, PC written with computed next PC
            - if load/store/arithemtic, PC writtenw ith PC+4
    - **Change PC to new PC**
    - ![blockdiagram](./img/processorblock.png)
    - Control Path vs Data Path
        - Control path: changing select bits of muxes, write-enable bits for RAM, op selection bits for ALU 
            - it's in blue from the picture
        - Data path: 
            - data and addr used by prog
            - it's in black 

- Executing Instructions (Low Level)
    - **Instruction Fetch**
        - use addr in PC, 32 bit addr
        - add 4 to PC to get next seq addr
        - must use flip-flops since feedback of prev computed PC value
        - ![fetch](./img/fetch.png)
    - **Decode and Register Read**
        - read what type of instr was read
        - read any source register ops

        - Decoding
            - split up bits from the instruction
        - Register read
            - register file
        
        - Immediate operands
            - sign extend immediate field
    - **Execute**
        - ALUSrc
            - select input to mux
        - ALU Control
            -  ALU depends on instr
                - opcode and funct
    - Accessing Memory
        - store instr: sb, sh, sw -> rs used to access reg file
    - Memory
        - 2^32 locations
        - each location = 1 byte
    
    - **Writeback**
        - arith and loads write back to reg file 
    - Branch Computation
        - reg 1 - reg 2 == 0 ? 
            - PC not PC+4 if 
                - beq, and ALU 0
                - bne, and ALU !0
                - PC + 4  + (SignExtImm <<2 )
    - Jump
        - jump=1 for j & jal
--- 
## Chapter 5
- Processor Performance Measurement
- Metrics
    - Total Execution Time
        - *Exe Time B / Exe Time A = X**
            - **speed-up** is x
            -  A is X times faster than B
    - Cycles per Instr
        - lower is better
        - **CPI = total cycles to exe / total # instructions**
    
    - Execution Time = num cycles * clock period 
        - num cycles 
            - num # exe * num clock cycles per instr
    - Frequency = 1 / clock period
    - Energy: capacity to do work, Joules, battery life
    - Power: rate of consuming energy, Joules/second (Watts)
        - more perf = higher power
    - Energy more meaningful to compare
    - Instruction per cycle
        - IPC = 1 / CPI
    - *Instructions per Second (IPS)**
        - takes frequency into account
    - IPS / W 
        - performance and power into single metric
- Benchmark
    - standard utilities
    - standarized 
    - **Phase**: small subset of benchmark execution
- Perf Improvements
    - longest circuit path / critical path
    - single-cycle processor limited by clock freq
    - main mem extremely slow
- Make sure ISA satisfied
    - look for patterns, add circuits to make efficient