# Notes 

## Security Definitions?
- **Triad Security**
    - Confedentiality, Integrity, Availability
- **Policy**
    - aim to acheive with respect to dependability
- **Mechanism**
    - mechanism to achieve policy
- **Assurance**
    - degree to which mechanism relied and how well they compose
- **Incentive**
    - motive to maintain/guard system
- **System**
    - product/component
    - also has an os, communications, and other infrastructure
    - or applications that include client and cloud components
    - IT staff, internal users and management, customers and external users
- **Players**
    - Subject
        - person / legal person (company / gov)
    - Role
        - set of functions assumed
    - Principal
        - entity/equipment/channel that participates in security system
    - Group
        - set of principals
    - Identity
        - two principals refer to same person/equipment
- **Trust**
    - Trusted
        - at risk to fail
    - Trustworthy
        - component won't fail
- **Secrets**
    - Secrecy
        - effect of mechamism to limit principals who can access info
        - cryptography/access controls
    - Confidentiality
        - obligation to protect secrets if you know them
    - Privacy
        - ability/right to protect personal info & prevent invasions
- **Data**
    - Integrity
        - untampered
    - Authenticty
        - untamepered and fresh
- **Attackers**
    - Vulnerabillity
        - property open to possibility of breaching security policy
    - Threat
        - subject exploits vulnerability to breach security police
    - Security failure
        - breach in security policy
- **Goals**
    - Protection
        - property, defined in way allows reasoning (general)
    - Security policy
        - statement of system protection strategy
    - protection profile
        - device-independent description allow comparative evaluations among products/version
    - security target
        - specifications set out by which security policy will be implemented in particular product

## Risk Management
1) Identifiy hazards
2) Assess hazards 
3) Develop controls and make risk decisions
4) Implement controls
5) supervise and evaluate

## Software Vulnerabilities
1) Input validation and representation
    - metachars, alt encodings, numeric representations
    - Examples
        - Buffer Overflow, Command Injection,number range, XML Validation, SQL Injection, Improper string termination, etc
2) API abuse
    - Contract between caller and callee
    - Examples
        - Avoid dangerous functions
        - functions with bad names or unexpected return values
        - authentication, exception handling, file sys, privilege management, strings, return value, etc
3) Security features
    - authentication, access control, confidentiality, cryptiography, privilege violation
    - Examples
        - least privledge violation, password management, privacy violation, missing access control, etc
4) Time and state
    - multithreading and distrbuted computation
    - Examples
        - Deadlocks, fail to create new session upon authentication, bad practices with threads stuff
5) Errors
    - error handling
    - Examples
        - empty catch block, too broad of error catching, throws errors
6) Code quality
    - memory leaks, obselete, unitialized var, using after free
7) Encapsulation Environment
    - misconfiguration, leftover debug code, cloneable without new, info leak, modification of private var

## Security Principles
- Economy of Mechanism
    - design simple and small as possible
- Fail-safe defaults
    - access: permission over exclusion
        - why someone can do sth rather than can't 
- Complete mediation
    - every access to every object must be checked for authority
- Open design
    - design not a secret, possession of things should be protected
- Separation of privilege
    - two keys better than one
    - multiple parties needed to allow access
- Least privilege
    - operate with least privileges to do job
        - ex: "need to know"
- Least common mechanism
    - minimize amt of mechanisms common to more than one user and depended on by all users
    - limits sharing
- Psychological acceptability
    - UI easy to use to apply protection mechanisms

## Fuzzing
- Do not write bugs
    - styongly-typed lang
    - well-designed system
    - understand language and system
    - consistent programming style
    - handle run-time errors
    - use security principles
- Tests
    - Can't be exhaustive
    - Things we don't expect can be unexpected
- Tools
    - static analysis (without running program)
        - GCC, Clang, linters
    - dynamic analysis (running program)
        - Valgrind, fuzzers
- GCC
    - GCC -Wall -Wextra -fanalyzer 
- Valgrind (Memcheck)
    - valgrind --leak-check=full PROG
- Fuzzing
    - Program under test, given unexpected values, repeat, maybe find bug
- Fuzz Testing
    - use of fuzzing to test if PUT violates correctness policy
- Fuzzer
    - program performs fuzz testing on PUT
- Fuzz Campaign
    - specific execution of fuzz on PUT with specific correctness policy
- Bug Oracle
    - program, determines whether given execution of PUT violates specific correctness policy
- Fuzz Configuration
    - parameter values that control the fuzz algorithm
- Classes of Fuzzers
    - Black box, can't observe internals
    - Gray box, some info about internals
    - White box, analyze interals to generate inputs
- Americal Fuzzy Lop (Gray box)
    - Fuzzer: AFL
    - Fuzz Campaign: program should not segfault
    - Bug oracle: Did it crash?
    - Fuzz config: PUT + seed + coverage
- Want lots of iterations
    - use small seed
- Challenges
    - huge input space
        - focus on subset, better mutations, apply gray/white box techniques, aim to execute all branches, seed trimming
    - Many inputs to same bug
        - use deduplication techniques
    - Writing driver applications   
        - wrapper class to allow fuzzer to interface because every program receives input differently 
    - Fuzzer might generate complicated crash inputs
        - use test case minimization techniques
- Applications
    - Software engineering, Bug bounties, Pentration testing
    - Understand program analysis better

## Kernel Mediations
- userspace / kernel / hardware
- userspace
    - programming environment programs run in
    - usually have direct access to CPU and memory
    - processor prohibits certain instructions/memory
- syscall
    - 
# Memory Errors
- Code Quality
    - uninitialized memory
        - 
        ```c
        int sign;
        set_flag(0, &sign);
        //sign non-deterministic at this point, not initialized

        void set_flag(int number, int *sign_flag) {
            if (number > 0) {
                *sign_flag = 1;
            } else if (number < 0) {
                *sign_flag = -1;
            }
        }
        ```
    - Use after free
        - content of mem is undefined
        - mem is inaccessible
    - Double Free
        - Realloc
        - 
- Help
    - -Wall -Werror
    - -fanalzyer

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

## git
- git config --global user.name "FULL NAME"
- git config --global user.email EMAIL
- git add files (. for all)
- git commit -m "Describe change"
    - -s, records person responsible
- git log
    - git log -p
- git diff before git add

## Debugging
- gdb program
- break functinName 
- print $rsp
- dissassemble functionName
- set stackValue = 

- Print data to file
- signal(IGPIPE, SIG_IGN); //ignore

## Other commands
- echo core >/...
    - redirect results of echo to
- sudo bash
- exit
- sudo tee
- chmod +x fileName
- file  name
    - gives info about the file

- xxd -i  fileName | less

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


# Exam 
- memory not asked
- allowed 1 page, 8x11