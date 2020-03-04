# CS 555 Notes
## Intro
- Total security cant be achieved

- Secure if 
    - cost atk sys > val of protected resources
    - time atk sys > time rsc have val

- External environment
    - unauthorized access
    - accidental introduction of inconsistency 
    - malicious modification / destruction

- Secure if maintains
    - **confidentiality**
        - proctect access and disclosure of info
    - **integrity**
        - protect improper edit/destruction, authentic
    - **availability**
        - timely and reliable acess to and use of info

- Terminology
    - asset: enetity to protect
    - attacker: intentionally attempts to violate security
    - threat: possibility of successful atk
    - security system: set of actions -> prevent conseqs
    - vulnerability
        - weakness in security sys
    - mitigation
        - action/policy/mech to diminish vulnerabilities
    - risk
        - measure likelihood and impact of atk

- Challenges
    - lots of weaknesses to address
    - social and phsyical access considered
    - need to update constantly

    - very costly and annoying 

- Vulnerabilities
    - Corrupted (loss of integrity)
    - Leak (loss of confidentiality)
    - Unavailable/Slow (loss of availability)

- Attacks
    - Passive vs Active
    - Insider vs Outsider

- Threat Classification
    - Interception (unauthorized disclosure)
        - gain unauth access
    - Interruption (disruption)
        - destroy/corrupt access to asset
    - Modification (usurpation) 
        - changes permissions on obj
    - Fabrication (deception)
        - introduces incorrect data/metadata

- Passive Vs Active
    - Passive
        - obtain information transmitted
    - Attack
        - alter system / operations
        - replay, masquerade, modification of messages, denial of service

- Attack Surface Categories
    - reachable and exploitable vulnerabilities
        - network, software, human attack surface

- Computer Security Strategy 
    - Security Policy 
        - rules & practices 
    - Security Impl
        - prevention, detection, response, recovery
    - Assurance 
        - degree of confidence 
    - Evaluation
        - examining product to criteria
- Bell-LaPadula (BLP) Model
    - Top secret > secret > confidential > restricted > unclassified 
    - subject has security clearance
    - objace has security classification

    - Access Modes
        - READ (read)
        - APPEND (write)
        - WRITE (read/write)
        - EXECUTE (only execution)
    
    - Limitations 
        - incompatability of confidentiality and integrity
        - Cooperating conspirator problem in the presence of covert channels 
- **Multilevel security**
    - no read up
    - no write down

- Authorization
    - permissions (owner -> delegate -> actions)
- Authentication 
    - who you are 

- Security Assurance 
    - deg of confidence 
    - not guarentee that measures work intended
    - reqs, security policy, product design, product impl, sys operation

    - target audiences
        - consumers 
        - devs 
        - evaluators 
    - Scope 
        - sys architecture, integrity, testing, design spec & verification, coverty channel analysis, trusted facility mgmt, trusted recovery, trusted distribution, config mgmt

- Evaluation 
    - features work correctly and effectively & no exploits
    - target of evaluation (TOE)
    - higher levels = more rigot, time, and cost
    - principle input: security target, evidence, actual TOE
    - Result: confirm security target is satisfied for TOE

    - Parties
        - sponsor: customer
        - dev: prov evidence
        - evaluator: confirm reqs satisfied
        - certifier: agency monitor eval process
    - Phases 
        - preparation: contact sponsor and dev 
        - evaluation: confirm satisfaction of security target
        - conclusion: report -> certifiers

---
## Software Security 
- fundamental security facts
    - complex sys has a flaw/bug to be fixed
    - hard to bulid hardware/software not vulnerable to attacks
- Terminology 
    - Trust: 
        - exent someone relies on sys -> confidence that sys meets specification
    - Trusted System: 
        - sys believed to enforce given set of attributes to stated deg of assurance 
    - Trustworthiness
        - assurance sys deserves to be trusted
    - Trusted computer system
        - sufficient hardware & software assurance on range of sensitive/classified info
    - Trusted computing base (TCB)
        - enforces particular policy, reistant to tampering and circumcention, small to be analyzed systematically
    - Assurance 
         - proc -> sys is dev and oper as intended
    - Evaluation
        - assess if product has properties claimed for it
    - Functionality 
        - features provided
    
- Security of Sys
    - consider: vulnerabilities, types of threats, and how they manifest
- economy of mechanism, fail-safe defaults, 
    complete mediation, open design, separation of privilege, least priviledge, least common mechanism, psychological acceptability, isoloation, encapsulation, modularity, layering, least astonishment

- program [algo, input processing, output generation], other progs [user progs, OS, DB], Devices [input: keyboard/mouse, output: display, network, storage & file systems]
    - who, when, reqs, design, impl, testing, deployment

- Program vulnerabilities 
    - reqs: validation and verification, rscs & access 
    - design: identify & reduce, hooks/logs 
    - impl: defensive prog, hooks/logs
    - testing: variety of testing
    - deployment: hooks/logs

- defensive programming
    - never assume anything, check all assumptions & handle any possible error states

- Correct Algo Impl
    - validation, rechcked, machine code corresponds, mem usage & interpretation

- Race Conditions 
    - without synchonization of accesses, value could be corrupt
    - due to writing concurrent code & correct selection 
    - Deadlock
        - processes/thread wait on resource held by other
        - programs to be terminated as soluation

- Software Security:
    - spec target bugs that result in failure, differ dramatically from expected, unlikely to be identified

- Software quality and reliability 
    - accidental failure, structured design and testing to identify & elim bugs, how often they are triggered

- Deployment 
    - gather data mechanisms, turn on features

- Different attacks
    - input size, buffer overflow 
    - injection attack 
        - input data -> influence flow of exe
    - Cross Site Scripting Attacks (XSS) 
        - input to one -> output to another

- Validating input syntax 
     -only accept known safe data
- Input Fuzzing
    - randomly generated data as inputs 
    - need combination of approaches for comprehensive coverage
    - Fuzzers (tool) [seed / mutation]

- **Principle of Least Privilege**
    - only give acess as necessary to perform task and no more
        - permission checked on each access
        - keep sys as simple as possible

- Exfiltration / Output
    - proper permissions before outputting data
    - use Principle of Least Privilege 

- Design Principles
    - Minimize attack surface
    - establish secure defaults 
        - security features turned on 
    - least privilege
    - defense in depth
        - multiple layers of control
    - fail secure
        - limit info exposed on errors
    - Economy of mechanisms
        - keep things simple
    - don't trust services 
        - access to all rsc are validated
    - separation of duties
        - diff entities have diff roles
    - open design
        - use proven open standards
    - psychological acceptability 
        - protect sys, not hamper users
    - weakest link
        - strong as weakest link
    - fix security issues correctly 
        - find issue, fix it, dev a test to detect it again

---
### OS-Security
- Programs exe on sys under contorl of OS
- Sys have multiple users

- Buffer Overflow
    - more data written than expected
        - how mem is laid out, funcs vulnerable, buffer size

    - malicious code and gain root access

    - identifying: inspect program source, trace exec, or tools 
- only  low-level and direct access to mem are vulnerable to overflow
    
- stack, heap, static data section, text segmaent, reserved

- Buffer located on stack 
    - save addr on stacks

- Target program
    - trusted sys utility
    - network service daemon
    - commonly used lib code

- Compile-time Defenses
    - modern high-lvl 
        - not vulnerable
        - flexibilty & safety for resource, 
        - additional code must be executed to impose checks
        - limited usefulness in writing code to devices
    
    - 1.  Safe coding techniques: audit and inspect any unsafe code
    - 2. language extension, safe libs
    - 3. stack protection: check entry and exit code for corrupt (random canary / Return Address Defender (RAD))
- Run-time Defenses
    - 1. executbale addr space protection (use virtual mem for some non-executable)
    - 2. Addr space randomization (manipulate key data structures / heap buffers / lib func locas)
    - 3. Guard pages between critical regions -> if access then abort process

- Variations on buffer overflow
    - stack buffer overflow
    - return to sys call
    - heap overflow
    - global data overflow 

- Replacement Stack Frame
    - stakc protection mechanism to detect, non-executable stacks, randomization of stack in mem & libs

- Return to Sys Call 
    - replace return addr with std lib func 
    - suitbale params on stack above return addr
    - detect mod, non-exe, random stack

- Heap Overflow 
    - heap -> no return addr 
    - make heap non-executable / randomize alloc of mem

- Global Data overflow 
    - overwrite func pointer later called 
    - non exe, random, move func ptrs, guard pages

- Role of OS
    - mng shared rscs in fair, efficient manner
        - process, mem, IO, files

- Process managment 
    - active entities that use rscs
    - CPU scheduling, interprocess comm (sockets/msg passing/files)

- Program
    - file with exe code 
    - process is prog in mem that is exe

- Process [prog loaded into mem & exe]
    - Kernel
        - interupt handlers, sys calls by kernel
    - Stack
        - func arcs, local vars, active funcs
    - Heap
        - dynamic alloc mem
    - Static Data 
        - data declared compile time
    - Text
        - machine code, PC
    - Reserved
        - bookkeeping info (tables)
    
- Program to Process
    - compile [high to low] -> Linking [lib code referenced] -> Loading [loaded]

- Process creationg
    - forking parent then exec child [copy parent, diff ret value, exec replaces mem of child with new exe]

- OS Design
    - daemons (pro run indepen of parent)
    - kernel mode (need support via interrupts/traps)

- Dual Mode 
    - process: user mode and kernel mode 
    - kernel mode only needed with OS/shared srcs
    - triggered by interrupts/traps 

- Interrupts, traps, sys calls 
    - interrupts 
        - hardware generated (div by 0)
    - Traps 
        - software generated (exeception?)
    - Sys calls 
        - call on spec interfaces 
    
- Memory Mgmnt
    - sharing of main mem among exe processes via virtual memory

    - Processor <-> main mem (RAM) <-> Secondary Memory (HDD)

- OS View
    - prog -> process
        - prog stored on disk, load into mem, create process
    - fetch instrs & data from locs
    - binding, loading, swapping, virtual mem, sharing, free memory, fragmentation

- Paging
    - allow physical addr space of process to be **noncontig**
    - Frames: phy mem -> fixed-sized blocks 
    - Pages: logic mem -> same size blocks
    - N pages = N free frames 
    - Page table 
    - Pros
        - no external fragmentation 
            - any free frame alloc to any process
            - worry about internal frag
        - 80/20, (LOCALITY)
            - 80% accessing, 20% mem
            - keep "active" parts only
        - shared pages 
            - shared libs & code between processes
        - processes not bound by physical mem
        - swapping more efficient (move pages instead of processes)
    - Cons
        - more complex addr lookup & expensive context switches 

        - Page Table (main mem, MMU finds it)
        - Translation Look-aside buffer (TLB)

    - Page Table Strategies
        - Single, Two, N-lvl page table
        - hashed / inverted page tables 

    - Page fault
        - ref page no in mem, trap to OS to bring in page from disk
    
    - Base Page replacement 
        - find loc of page on disk
        - find free frame 
            - free frame? use it
            - else, page replacement 
            - write victim frame to disk, update page & frame tables
        - Read desired page into free frame and update page & frame tables 
        - restart the user process
    
- Segmentation
    - support users view of mem
    - pros
        - no inteneral frag
        - small tables 
        - impl some mem proctection policies more easily
    - cons
        - external fragmentation
        - costly mem mgmt algos

- File-System Interface
    - most visible aspect of OS
    - Parts
        - Files [storing data]
        - Directory Structure [orgnize files]

    - File, logical storage unit
        - create, delete, open, close, read, write, seek
        - Attributes 
            - name, identifier, type, location, size, protection, time, date, user ID
    - Directories 
        - goals: efficiency, naming, grouping

- Protection
    - control access to sys by limiting types of file access permitted
- Security 
    - authentication of sys users to protect integrity of info 

- Policy 
    - statements of what states are allowed vs not allowed

- Access Control Bits
    - -RWXRWXRWX
    - <user, group, world>, <read, write, execute>

- Design principles from Multics
    - "protect mechanisms on permission"
    - check access for current auth
    - design not secret 
    - principle of least privlege
    - natural human interface design

- UNIX Protection Domain
    - setuid = on, user-id => owner of file, after exec then user-id reset

- Access Matrix
    - separates mechanism from policy
    - Mechanism
        - matrix manip by auth and rules enforced
    - Policy 
        - who can access in what mode, User dictacts policy
    - * denotes access right can be copied  
    - Impl
        - GLobal Table 
            - <Domain, Object, Right-set>
            - too large, no grouping
        - Access List 
            - <domain, right-set> per obj
            - 1 list per col in access matrix, determining access rights for all domains difficult
        - Capability List 
            - list of objs & operations
            - 1 list per row in access matrix, revoking capabilities can be inefficient 
--- 
