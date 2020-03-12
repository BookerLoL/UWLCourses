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
## Authentication 1
- ** first line of defense **
- process of verifying an identity claimed 
- 2 steps: identification and verification
- str needs to match value of assets protected

- E-Authentication Architectural Model (NIST SP 800-63-2)

- 4 means of authenticating user identity 
    - 1)  knows 
        - password, pin..
    - 2) owns (token)
        - smartcard, physical key..
    - 3) is (static biometrics)
        - fingerprint, retina, face..
    - 4) does (dynamic biometrics)
        - voice pattern, handwriting, typing rhythm
- Passwords (12+ chars)
    - use diff pw
    - pw manager [Lastpass, 1password, splashID]

- Password Authentication
    - login & pw
    - user id -> authorize access, privleges

- Password Brute Force Attacks
    - Offline dictionary atk
        - pw file -> common pw checks
        - prev unauth access to pw file, intrusion detection
    - Specific Account atk
        - pick user & guess until finds one
        - limit access after X attempts
    - Popular password atk
        - common pw
        - enforce good pw / authentication reqs from same IP addr
    - Electronic monitoring 
        - encrypted pw
    
- Password Social Engineering Atks
    - guessing against single user
        - knowledge to guess
        - pw training & resetting
    - Workstation hijacking
        - unattended 
        - automatic locking after period of inactivity, or diff user behavior
    - user mistakse
        - written down / shared
    - multiple pw use
        - same pw for many 
        - enforce unique pw 

- Unix PW Scheme
    - Salting (randomly generated)
        - duplicates not detectable
        - incr difficult of offline dict atks
        - same user use same pw? difficult
    - impl
        - MD5 
        - **most secure: Blowfish**

- Pw Cracking
    - dict atks
        - large dict of possible pws -> each pw hashed using salt & compared to stored hash values
    - rainbow table atks
        - pre-computer hash values for all salts
        - counter using large salt value and hash length
    - exploit easy pw
        - shorter pw easier to crack
    - John the Ripper
        - open source -> brute-force + dict 

- Approaches 
    - complex pw policy, but cracking has improv

- storing encrypted pw
    - deny access -> blocks offline guessing
        - shadow pw file
        - avil to privledged users
- password selection strategies
    - user edu
    - gen pw
    - reactive pw checking
        - sys periodicaly runs pw checker to find guessable pws
    - complex pw policy 
        - user select but not guessable pw

- password checking
    - rule enforcement 
    - pw cracker [can't use these]
    - blook filter [check if pw is member of set]
- PW
    - Pro
        -  cheap to impl, change, reset, stolen pw affects 1 sys, convenient, privacy
    - Con
        - easy to hack, choose bad pw
    - sol
        - edu, better pw, pw file protection, 2-factor auth, pw mngr

- Memory Cards
    - store, not process data 
    - physical acess to things 
    - req special reader, loss of token, dissasfaction

- Smart Tokens
    - embedded microprocessor
    - keypad and display, compatible reader/writer
    - Authen protocol
        - static
        - dynamic pw gen
        - challenge-response
- Smart Cards
    - credit-card like, electronic itnerface, any smart token protocol
    - 3 types of mem
        - Read-only mem [only stores data]
        - electrically erasable programmable ROM [hold app data & prog]
        - Random access mem [hold temp data when exe]

- Electronic ID cards
    - national id for citizens, verified by gov 

- Biometric Authentication
    - unique physical characteristics 
    - complex & expensive vs pw & tokens

- Remote User Auth
    - auth over network/internet/comm link complex
    - threats -> use challenge-response protocol

- Auth Security Issues
    - eavesdropping
    - host atks
        - atk user file hosting pw
    - replay
        - repeat captured user response
    - client atks
        - attempt user auth w/o remote hose
    - trojan horse
        - pretents to be sth to capture pw
    - dos
        - disable service -> flood with numerous auth attempts

- Eval Auth
    - impl cost, convenience, ability to impersonate, risk associated

    - Risk Assessment 
        - Assurance lvl
        - potential impact 
        - areas of risk
- Assurance lvl
    - deg confidence credential refer to identity
    - Levels
        - 1. little/no confid in asserted identity validity
        - 2. some 
        - 3. high 
        - 4. very high

- Potential Impact 
    - Low: limited adverse effects
    - Moderate: serious adverse effects
    - HIgh: severe adverse effects

- Cards, Tokens, etc
    - pros
        - strong than bad pw, auth with humans, multiple types of data stored & presented in diff format 
    - cons
        - reader for device (expensive), can lose
    - sol
        - software tokens (one time codes)
        - two-factor auth

- Biometrics comparing
    - accuracy, reliability, long term stability, ease of use, speed, universality, hygienic lvl, cost

    - FAR: false accept ratio
    - FRR: false reject ratio
    - Goal: accurate to FAR and FRR within patience lvl 

    - Incr security == decr convenience 

    - pro: easy to remember, convenient, fast, more secure than pop pw, unq/indvd
    - con: biofactors change, hacked/stolen-> can't change bio features, comput expensive, accurancy not great, convenience and social acceptance
    - sol: multifactor -> mitigate downsides 

- Multifactor Auth
    - pro: security of both mechanisms, solve many issues with pw & biometrics 
    - cons: inconvenience, complx to mng & impl

--- 
## Access Control
- Access Control Policies
    - Discretionary access control (DAC)
        - based on identity of req & access rules
        - gran entity access
    - Mandatory (MAC)
        - comparing security labels with clearances
    - Role-based (RBAC)
        - roles uses have 
    - Attribute-based (ABAC)
        - attr of user, resource accessed, environmental conds

- Subject, Objects, Access Rights 
    - subject: entity capable of accessing objs [owner, group, world]
    - object: rsc 
    - access right: what sbj may access obj

    - Access Matrix
        - Y, subjects 
        - X, objects
        - inside are access rights (own, read, write..)
- Protection domains
    - ability ot group subjects and sets of objs and rights
        - access right = <obj name, rights set>
        - rights-set 
        - domain 
- UNIX File Access Control
    - SetUID
    - SetGID
    - Sticky bit (only owner can modify file)
    - superuser (sys-wide access)

- MULTICS, lower-lvl = more privileges

- DAC
    - pro
        - straight forward, familar, popular
    - con
        - not scalable, error prone and vulnerable, difficult to change many permissions,
        takes up a lot of space
- RBAC
    - role assignment, role authorization, permission authorization
    - pros
        - grouping sbjs, obs, & access rights more efficient 
    - cons
        - tied to user identity, diff to scale to new role types, diff to manage permissions for obj across many roles
- ABAC
    - sbj, obj, and enviro attrs
    - pros
        - define auths -> express conds on properties of both rsc & sbj
        - flexible and expressive power 
        - scalable to unknown types & objs (cloud)
        - quickly react to security events
    - cons
        - slow when many predicates & gather data from many srcs 
        - tool support not mature 
        - diff to setup and understand coming from other models
- XACML
    - eXtensible Access Control Markup Language
        - standard defines declarative attr-based access contorl policy lang
        - PAP, PDP, PEP, PIP, PRP
## Cryptography
- Encryption 
    - way to maintain confidentiality  [entities to access data]
- str lies in the key

- terminology
    - message
        - data to be encrypted
    - key
        - info for encrypt
    - encryption 
        - substitutions and transformation
    - decryption
        - reverse of encryption
    - cipher text
        - encrpyted message 

- Independent dimensions
    - type of operation
        - substitution (new mapping)
        - transposition (rearrange)
    - num of keys
        - symmetric (same key)
        - assymmetric (diff key)
    - plaintext is processed
        - block vs stream cipher

- Symmetric Encryption
    - DES
    - 3DES (DES 3x, longer key)
    - AES 

- DES
    - 64 bit plaintext block, 56 bit key -> 64 bit ciphertext block

- Triple DES (3DES)
    - pro: 168-bit key length
    - con: sluggish and 64-bit block size

- key size 
    - more key combinations, more time to decrpyt

- Encrpytion ideas
    - Confusion: substitution
    - Diffusion: transposition
    - secrecy only in the key

- Know how AES works

- Block Cipher
    - 1 input of block eles
    - output block each input 
    - reuse keys
    - more common

- Stream Cipher 
    - input eles continuously
    - 1 output ele at a time
    - generally faster & use less code
    - encrypters plaintext 1 byte at a time
    - pseudorandom, unpredictable without key
- Random vs Pseudorandom
    - seq of numbers satisfy statistical randomness tests

    - True random number generator (TRNG)
        - nondeterministic, unpredictable natural prcoesses (gas)
    
- RC4 Cipher (Stream)
    - permutation of data based key
    - pros: faster than most block ciphers, strong if good key
    - cons: long term key makes cipher text vulnerable, modern approaches refresh/change keys (RC4 doesn't)
    - widely used (WEP, WPA, SSL, SSH, PDF, Skype, etc)

- Block Ciphers
    - ECB, Electronic Codebook
        - each block with same key, simple & fast, not great for long msgs/images
    - CBC, Cipher Block Chaining
        - XOR of current plaintext block & prev block
    - CFB, Cipher Feedback 
        - sim to CBC, ciphertext used as key for next block
    - OFB, Output Feedback 
        - sim to CFB
        - encryption & decryption same
        - block -> stream cipher
    - CTR, Counter 
        - block -> stream
        - combine with a nonce
        - not good alone

## Public-Key Cryptosystems 
- Key distribution 
    - 1) key selected by A, physically delivered to B
    - 2) third party select key & deliver
    - 3) use recent key, transmit new key to other encrypted by old key
    - 4) encrpyted connection to C, C deliver encrypted links 

- reqs for public-key cryptosystems 
    - easy to create key pairs
    - sender knowing pub key to encrpyt
    -  receiver knowing key to decrypt, 
    - infeasible for opponent to det prv key
    - infeasible for oppon to recover org msg 

- Asymmetric Encryption Algos
    - RSA
        - plaintext & ciphertext are ints
    - Diffie-Hellman key exchange 
        - reach agreement abt shared secret as secret key
    - DSS, digitial signature standard
        - only for digital signature with SHA-1
    - ECC, elliptic curve cryptography
        - like RSA, but smaller keys

- Man-in-the-middle attack
    - intercepts msg, pretends to be other people

- RSA 
    - Encrypt: C = Me mod n
    - Decrpy: M = Cd mod n = Med mod n 
    - both sender & receiver know n & e
    - only receiver knows d

- Security of RSA
    - bruther force, mathematical atks, timing atks, ciphertext atks, matter of time 

- ECC
    - pro: smller keys, newer -> less methods of hacking
    0 256-bit key is 20x fsater than RSA 2048 signature
    - consL str depends on elliptic curve

- Msg Digest & Hashes
    - 1-ways funcs, can't recover input [hard to reverse]
    - 2^bit digest

- **MAC** Message Authentication Code

- Secure Hash Algo
    - SHA 
        - uses bitwise XOR
        - collision: 2^ n/2
    - SHA-3 
        - hash values lengths: 224, 256, 384, 512 bits
        - process small blocks 
    
- HMAC
    - dev MAX from hashcode 
    - mandator-to-impl MAC for IP security
    - Objectives
        - use avail hash funcs w/o modifications
        - easy replaceability of hash
        - preserve org performance of hash 
        - use & handle keys in simple way
    - Security 
        - depends n cryptographic str of hash func
        - lvl of effort
            - brute force key 
                - O(2^n) 
            - finds collision
                - O(2n/2)
- Digital Signatures
    - auth both src & data integrit
    - encrpyting hash code with prv key
    - no confidentiality [not safe from eavesdropping]
--- 
## Cryptanalysis
- measure str of cryptographic algo & how vulrnerable

### Intrusion 
- Security Intrusion: security event(s), intruder gain/attempts/access sys w/o authorization
- Intrusion Detection: monitor & analze events to find attempt access rsc in unauth manner.
- Intrusion Dection System (IDS)
    - requirements
        - run continually, fault tolerant, resist subversion, min overhead on sys, adapt to changes, scale monitor large num sys, reconfig
    - Host-based (HIDS)
        - single host for sus activity
    - Network-based (NIDS)
        - network traffic analyzes protocols, network
    - Distributed/hybrid IDS
        - host & network using sensors

    - Comprises of 3 logical components
        - Sensors: collect data
        - Analyzers determine if intrusion occurred
        - User interface: view output or control sys behavior
- Analysis Approaches
    - Anomaly detection
        - data abt behavior of legitimate users over period of time
    - Signature/Heuristic detection
        - set of known malicious atk rules
        - misuse detection
        - only notify known atks for a pattern
- Anomaly Detection Classification Approaches
    - Statistical
    - Knowledge 
    - Machine learning
- Signature Approach
    - large coll of patterns 
    - minimize false alarm rate & detect mal data
- Rule-based Heuristic
    - rules for iden penetrations

- Host-based intrusion detection
    - specialized lay of security
    - use either anaomaly/sig/heuristic 
    - monitor activity to detect sus behav