# CS 571 Computer Networks

- Case study topics
  - Cloud computer/mobile cloud computing
  - Cellular networks
  - IoT
  - Localization and location-based services
  - Mobile sensing and applications
  - Network security and privacy
  - Vehicular networks
  - WLAN, WPAN, RFID, NFC
  - Connected unmanned aerial/terrestial/underwater systems
  - AI Applications for Networking and IoT
- Case Study
  - Write proposal
  - Write literature review
    - IEEE paper format
  - Presentation for class
    - 10-15 mins
    - background might be explained?
    - topic should tie to Computer Networks

## Notes

- Computer Networks
  - collection of computers interconnected by single technology
    - exchange info
    - **Internet**
  - Connections
    - copper wire, fiber optics, microwaves, infrared, satellites
- Distributed System

  - Collection of computers appears as single coherent system
    - **World Wide Web**
  - Built on top of a network

- Network Uses

  - Business application -> **resource sharing (info)**
  - **Communication medium**
    - email
    - IP Telephone / Voice of IP (VoIP)
      - calls via network
    - **Desktop sharing**
  - **e-commerce**
    - business via electronically
  - Home Applications
    - **connectivity**
  - Entertainment
    - TV via IPTV (IP TeleVision)
  - Ubiquitous computing
    - power-line networks
    - Radio Frequency IDentification (RFID)
      - chips on cards and etc
  - Mobile Users
    - Wireless hotspots (802.11 standard)
      - fixed wireless vs mobile wireless

- **Client Server Model**
  - Network as middleman
  - Client on simple machiens
  - Servers are powerful computers
  - Client sends request, server sends back response/reply
- **Peer-to-peer (P2P)**
  - find nearby people to communicate with

### Broadcast/Point-to-Point Networks, PAN, LAN

- Communication model

  - Basic Model
    - Source, Transmission, Receiver
  - exchange of data between 2 parties

  1. Source (computers / telephones / etc)
     - produce data stream
  2. Transmitter
     - encode data -> signals
  3. Transmission System
  4. Receiver
     - decode signal -> data
  5. Destination

- Network Taxonomy (classifying networks by)

  - Types of Transmission Technology
    - broadcast networks
    - point-to-point networks
  - Scale (Distance)
    - LAN, MAN, WAN

- Point-to-Point link
  - connect individual pairs of machines
  - packets, routes
- Unicasting

  - point-to-point with exactly 1 sender and receiver

- Broadcast Networks

  - single communication channel shared by all machines on network
    - each machine receive same packet
      - address field defines recipient on packet
      - if not recipient, discard packet
  - Broadcasting
    - packet received and processed by every machine on network
    - can address packet to all destinations
  - Multicasting

    - packet transmitted to subset of machines

- q1: 1, 2, 3
- q2: source, transmission, destination

- LAN connects devices over the range of a person.

  - router
  - A network service provider is a subnet operator which is also known as ISP (Internet Service Provider).

- Scale (Distance)

  - interprocess distance
    - 1m (square meter) = personal area network (PAN)
    - 10m-1km (room, building, campus) = local area network (LAN)
    - 10km (city) = metropolitan area network (MAN)
    - 100km-1000km (country, continent) = Wide area network (WAN)
    - 10,000 km (planet) = internet

- Personal Area Network (PAN)
  - connect devices over range of a person
  - Bluetooth
    - Master-slave paradigm
      - PC is master
        - what addresses to use, when can broadcast, how long can transmit, freq
      - mouse, keyboard, headphones are slaves
- Local Area Network (LAN)
  - Wireless LAN (802.11 - WiFi)
    - Access point (wired to network)
      - relays packets
  - Switched LAN
    - various network segments are interconnected using switches
    - Ethernet
      - copper wire / optical fiber
      - Ethernet Switch
        - has multiple ports computer can connect wire into
        - at most 1 machine can successful transmit at a time
  - Enterprise networks
    - companies using LAN
- Wide Area Network (WAN)

  - ex: connect 3 branch offices
  - Subnet
    - collection of routers and communication lines owned by network operator
    - components
      - **transmission line**
        - moves bits
        - copper wire / optical wire / radio waves
        - usually leased from telecom company
      - **router**
        - 2 routers connected by transmission line
    - Internet Service Provider (ISP)
      - network service provider (NSP, also considered ISP)
        - connects networks
      - buy connectivity from ISP to use it
      - no shared transmission line, share indirectly via intermediary routers
        - use routing algorithm
  - VPN (Virtual Private Network)

    - virtual links run on top of internet
    - pros
      - flexible reuse of resource
    - cons
      - lack of control of underlying resource (varies by company)

  - Implementations
    - **Circuit Switching**
      - dedicated communication path
        - seq of physical links between nodes
          - logical channel on each link
            - data routed without delay
      - rapid transmission
        - fixed data rate
      - ex: telephone network
    - **Packet Switching**
      - send small chunks called packets
        - node to node, source to destinatino
      - usage
        - terminal-to-terminal computer, computer-to-computer communications
      - Lots of overhead to prevent errors
        - extra bits on packets to reduce redundancy and additional processing at nodes
    - **Frame Relay**
      - Take advantage of high data rates and low error rates
        - strip out overheadinvolved with error control
      - up to 2 Mbps
      - variable length packets called frames
    - **Asynchronous Transfer Mode (ATM) / Cell Relay**
      - circuit switching + packet switching
      - fixed-length packets called cells
        - 10-100s Mbps and in Gbps
      - allow multiple channels with data rate dynamically set on demand

- Metropolitan Area Network (MAN)

  - middle ground of LAN and WAN - centralized topology
    WAN connects devices over a country.

- Summation
  - Sum of Arithmetic Sequence (ex: n = 4, 0 + 1 + 2 + 3 + 4)
    <img src="https://latex.codecogs.com/gif.latex?\sum_{i=0}^{n}i&space;=&space;\frac{n(n&plus;1)}{2}" title="\sum_{i=0}^{n}i = \frac{n(n+1)}{2}" />
  - Sum of Proportional Sequence
    - <img src="https://latex.codecogs.com/gif.latex?\sum_{i=0}^{n}a^{i}&space;=&space;\frac{1&space;-&space;a^{n&plus;1}}{1-a}" title="\sum_{i=0}^{n}a^{i} = \frac{1 - a^{n+1}}{1-a}" />
- Binary Tree

  - Complete full binary Tree
    - height, h starts at 0 index
    - **total nodes, <img src="https://latex.codecogs.com/gif.latex?n&space;=&space;2^{h&plus;1}&space;-&space;1" title="n = 2^{h+1} - 1" />**
      - sum of proportional sequence, a = 2
    - given nodes, height, <img src="https://latex.codecogs.com/gif.latex?h&space;=&space;lg(n&plus;1)&space;-&space;1" title="h = lg(n+1) - 1" />
  - Balanced binary tree
    - Balanced if
      - left and right subtrees height differ by at most 1
      - left subtree is balanced and right subtree is balanced
        - check each subtree
    - Balanced full binary tree
      - **height, <img src="https://latex.codecogs.com/gif.latex?h&space;=&space;\left&space;\lceil&space;lh(l)&space;\right&space;\rceil" title="h = \left \lceil lh(l) \right \rceil" />**
        - l is number of total leaves
        - number of nodes on Kth level = m <= 2^k
          - number of leaves = l <= 2^h
      - **Number of leaves In full binary tree**
        - <img src="https://latex.codecogs.com/gif.latex?l&space;=&space;(n&space;&plus;&space;1)&space;/&space;2" title="l = (n + 1) / 2" />

## Basic Probability

- Event A is subset of S
- Probability is a function which associates A with number between 0 and 1
- measure change/likelihood A will happen
- Properties

  - P() is probability function, event A
    - P({}) = 0
    - P(A) <= 1
    - P(A') = 1 - P(A)
      - A' is complement of A
    - P(S) = 1
  - If outcomes S are equally likely
    - P(A) = #A / #S
      - #A is total elements in A

- Product Rule
  - job consisting of K seperate tasks
    - ith job can be done in ni ways for 1 = 1, ...k
    - job can be done in n1 x n2 ... nx nk ways
- Expectation / Expected Winnings
  <img src="https://latex.codecogs.com/gif.latex?E\left&space;[&space;X&space;\right&space;]&space;=&space;\sum_{i=1}^{k}&space;x_{i}p_{i}" title="E\left [ X \right ] = \sum_{i=1}^{k} x_{i}p_{i}" />
  - x are outcomes
  - p are probabilities
  - ex:
    - Roll die
      - 1, 2, 3 then you win $2
        - 3/6 chance for 2
      - 4, 5, 6 then you lose $1
        - 3/6 chance for -1
      - (.5 \* 2) + (.5 \* -1) = expected winnings
- Permutation
  - Choose k subjects out of n subjects **without replacement, order matters**
    <img src="https://latex.codecogs.com/gif.latex?P_{k,&space;n}&space;=&space;\frac{n!}{(n-k)!}" title="P_{k, n} = \frac{n!}{(n-k)!}" />
- Combination
  - Choose k subjects out of n subjects **without replacement, order does NOT matter**
    <img src="https://latex.codecogs.com/gif.latex?C_{k,&space;n}&space;=&space;\binom{n}{k}&space;=&space;\frac{n!}{(n-k)!k!}" title="C_{k, n} = \binom{n}{k} = \frac{n!}{(n-k)!k!}" />
- Binomial distribution

  - n, total number of independent experiments
  - p is discrete probaility distribution of number of successes in sequence of independent experiments
  - propability of p
    - failure: 1 - p
  - Bernoulli trial / experiment
    - single success/failure experiment
  - Bernoulli distribution
    - n = 1, k = 0 or 1
  - Equation
    <img src="https://latex.codecogs.com/gif.latex?Pr(X&space;=&space;k)&space;=&space;\binom{n}{k}&space;p^{k}(1-p)^{n-k}" title="Pr(X = k) = \binom{n}{k} p^{k}(1-p)^{n-k}" />
    - combination x success x failure

## Internet and Layer Design Issues

- internetworks / internet
  - collection of interconnected networks
  - generic sense
- Internet (Specific internet)

  - ISP networks to connect enterprise networks, home networks, and other networks
  - evolved from ARPANET
    - first operational packet network
    - developed to solve problem of communicating across arbitrary, multiple, packet-switched networks
    - Led to Foundation of TCP/IP protocol suite
  - Internet operations
    - data -> sequence of IP packets (diagram)
    - packet -> travel series of routers and networks
    - each router, receive packet, decide routing and forward packet

- Internet Architecture
  - Network Access Point (NAP)
    - ISP can connect with one another in peering arrangements
      - ex: gov to commercial (ISP to ISP)
  - Point of Presence (POP)
    - 2+ networks/communication devices build connection with each other
      - ex: local user connect with company network (ISP)
- Customer-premises Equipment (CPE)
  - communication equipment located onsite with the host
    - service and capacity
  - ex: DSL, cable modem, setlett
- Internet Example Configuration
  - IP backbone network at center
    - At periphery
      - edge routers / aggregate routers
      - provide connectivity to external network and user
  - Large enterprise network
    - ATM (Asynchronous Transfer Mode) WAN
    - router with firewall
  - Small/business
    - ethernet LAN configuration
    - router to the internet (DSL)
  - Mobile devices
    - through cellular network
- Protocol Architecutre
  - Need for protocol architecture
    - data exchange involve complex procedure
    - better if task broken into subtasks
    - subtask modules arranged in vertical stack
    - implement each module separately
      - layers shouldn't affect other layers
      - provide functions to perform communication for layers above
      - use functions provided by layers below
    - Peer layers communication with protocol
  - Key features of Protocol
    - set of rules that allow peer layers to communicate
    - components
      - syntax
        - forms of data blocks
      - semantics
        - control information and error handling
      - timing
        - speed matching and sequencing
- Network Software

  - No exact architecture / topology of internet
  - Network structured in layers
    - built upon layers below
    - reduce design complexity
  - Protocol Hierarchy
    - Layer n communication process
      - virtual communication
      - data and control to the next lower level
      - interface between each pair of adjacent layers
    - Peers
      - entities comprising the corresponding layers on different machiens
      - communicate using protocol to talk to each other
    - Physical medium
      - actual communication occurs
    - Network architecture
      - set of layers and protocols
      - hides detail implementation and specification of interfaces
    - Protocol stack
      - list of protocols (1 protocol per layer)
    - Lower protocol layers often implemented/embedded in hardware/firmware

- Layer Design Issues
  - Addressing
    - Network growth and evolution
    - Addressing
      - network layer
    - Considerations
      - Each layer have mecanism to idenify sender and receiver addresses
      - Rules for data transfer
        - single direction
          - simplex communication
        - bidirection but only one direction at a time
          - half duplex communication
        - bidirectional including both directions simultaneous
          - full duplex communication
  - Error Control
    - Reliability despite failures
    - Codes for error detection/correction
      - data link layer
    - Considerations
      - error detection
        - find and resend
      - error correction
        - recover info
      - Tradeoff?
        - need to redundant info
      - used at low layers
  - Flow Control
    - Reliability despite failures
    - Error/flow control
      - transport layer
    - Consideration
      - piecing messages together from receiver
      - accomodating for differnet message lengths
  - Multiplexing
    - Allocation of resources like bandwidth
    - Multiple access
      - medium access layer
    - Congestion control
      - network and transport layers
    - Consideration
      - multiplex/demultingplexing to share network dynamically
        - according short-term needs host
    - Situation
      - cost ineffective or impractice to set up individual connections for each pair of communiction press
  - Routing
    - reliability despite failures
    - routing around failures
      - network layer
- Relationships between services and protocols

  - adjacent layers in protocol architecture

    - Primitives
      - specify function to be performed
    - Parameters
      - use to pass data and control info

  - Primitive Types
    - Request
      - issue by service user to invoke service and pass params to specify request service
    - Indication
      - service provider
        - indicate procedure has been invoked and provide associated params
        - notify user of provider intiated action
    - Response
      - issued by service user to acknowledge or complete some procedure previously invoked by indication
    - Confirm
      - service provider to acknowledge or coplete procedure previously invoked by request
  - Confirmed Service
    - request, indication, response, confirm
  - Nonconfirmed service
    - request and indication

- Connection-Oriented vs Connectionless Services
  - Connection-oriented service
    - modeled after telephone system
    - user stablshes connection, uses connection, release connection
    - **connection prior to communication**
      - reliable
        - file trasnfer
        - movie download
  - Connectionless Service
    - modeled after postal system
    - each message carries full destination address and routed through intermediate inside the system independently
    - **messages handled separately**
      -example
      - spammers and junk email
- Services and Protocols
  - Service
    - set of operations/primitives that a layer provides to the layer above it
    - nothing about implementation
    - interface between two layers to provide service (**vertical**)
      - lower layer
        - service provider
      - upper layer
        - service user
  - Protocol
    - set of rules governing format and meaning of frames, packets, or messages exchanged by peer entites within layer
      - protocols to implement service definition
      - free to change protocols at will
      - service visible to users
      - service and protocol completely decoupled
    - layer talks to peer using protocol (**horizontal**)

## Reference Models

- Reference Models

  - describes layer in network architecture
  - models

    - OSI reference model

      - model is useful, but protocols are not
      - layers

        1. Application

           - provides functions needed by user

        2. Presentation

           - syntax and semantic of info transmitted, different representations

        3. Session
           - manage task dialogs, synchronization
        4. Transport

           - provide end-to-end delivery

        5. Network
           - sends packets over multiple links, determines how to send packets
        6. Data Link
           - sends frames of info
        7. Physical
           - send raw bit signals

    - TCP/IP reference model

      - model is not useful, but protocols are useful
      - layers

        1. Application

        - provides session and presentation functions

        2. Transport

        - provide end-to-end delivery

        3. Internet

        - deliver IP packets where they are supposed to go

        4. Link

        - interface bewteen host and transmission links

      - Protocols in each layer
        - Application
          - HTTP
          - SMTP (email)
          - RTP (real time mediums / voice / movies)
          - DNS
          - FTP
        - Transport
          - TCP
          - UDP
        - Internet
          - IP
          - ICMP
            - Internet control message protocol
            - error reporting
            - sends and creates messages router/service/host can't be reached for packets
            - any IP networked device can send/receive/possess ICMP messages
        - Link
          - DSL
          - SONET (synchronous data transmission)
          - 802.11 (wireless local network)
          - Ethernet

- Standardized Protocol Architectures
  - layer architecture
  - functions well defined
  - standards developed independently for each layer
  - boundaries well defined
    - changes in one layer won't affect layer in another
  - Lower layers concerned with more levels of detail
  - each layer provides services to higher layer
  - layer standards
    - service definition
      - functional description for internal use
      - what services are provided
        - not how
    - addressing
      - service access point (provides service to next higher layer)
    - protocol specification
      - precise syntax and semanics for interoperability
      - protocol elements
        - format of PDU (protocol data units)
        - semantics of all fields
        - allows sequence of PDU
- TCP/IP Protocol Architecture
  - result of protocol research and developed conducted on ARPANET
    - ARPANET, experimental packet-switched network
    - funded by Defense Advance Research Projects Agency (DARPA)
  - TCP/IP protocol suite
  - Protocol suite, comprises large collection of standardized protocols
  - Layers
    - Physical Layer
      - physical interface between comuter and network
      - concerned with
        - characteristics of transmission midium
        - signal levels
        - data rates
    - Data Link Layer / Network Access
      - exchange data between end system and attached network
      - concerned with
        - destination address provision
        - access to and routing data across network link between two attached systems
        - invoking specific services like priority
        - software depends on type of network used
      - allows layers above to ignore link specifics
    - Internet Layer (IP)
      - implemented in all end system and router
      - allow data to traverse multiple interconnected networks
      - provide communication between devices that are attached to diff networks
      - IP protocol to provide routing function
      - implemented in end systems and routers
        - routers connect two networks and relay data between them
    - Transport Layer (TCP)
      - host-to-host layer
        - only implemented in end systems
        - keep track of data to assure all are delivered reliably to appropriate application
      - common layer shared by all applications
      - provides reliable delivery of data
      - data exchanged reliably is requried
        - all data arrive at destination
        - in same order as sent
    - Application Layer
      - provude support for user applications
      - need separate module for each type of application
- Operation of TCP and IP

  - steps
    - process on Host A, send message to process on Host B
    - TCP -> msg -> IP with instructions to send it to host B
    - IP -> packet -> network access layer with instructions to send to router J
  - Addressing Requirements
    - each host on subnet needs unique network address
      - IP address
    - each application on host needs unique address within host
      - port
  - Data transmission
    - User data by application of byte styeam
    - TCP segment: TCP Header + data
    - IP datagram: IP header + TCP segment
    - Network-level packet/frame: Network header + IP datagram

- Transmission Control Protocol (TCP)

  - usual transport layer
  - TCP segment, basic protocol unit
  - Provides reliable connection for transfer of data
    - connection, temporary logical association btween two entities in different system
    - logical connection, given pair of port values
  - Tracks segments between entities for duration of each connection
    - to regulate flow of segments
    - to recover from lost/damaged segments
  - TCP Header

    - 20 octects, 1 octect = 8 bits
    - source port | destination port
    - flow control and error control
      - sequency number
      - acknowledgement number
      - window
    - checksum 16 bit to detect errors in TCP segment
    - Header length 4 bit
    - Reserved 3 bits
    - Flags, 9 1 bit flags

- User Datagram Protocol (UDP)

  - alternative of TCP
  - no guaranteed delivery
  - no preservation of sequence
  - no protection against duplication
  - Minimum overhead
    - enable procedure to send message to other procedure with minimum of protocol mechanism
  - connectionless
  - adds port addressing to IP
  - UDP header
    - Source port | Destination port
    - checksum, optiona

- IP Header (IPv4)

  - minimum of 20 octects
  - IP Datagram / packet
  - 32 bit source and destination address
  - header checksum
  - protocol
  - Id, flags, fragment offset
  - version, IPv4 = 4
  - IHL, size of header
  - DS, real time streaming
  - ECN, ene to end notification injection without dropping packets
  - total length, entire packet size in bytes including header and data

- IPv6

  - 1996, developed by IETF (Internet Engineering Task Force)
  - Functional enhancements of existing IP
  - accomodate higher speeds and mix of data streams
  - Driving force: need for more addresses
  - IPv6 includes 128-bit source and destination fields

- OSI vs TCP/IP
  - OSI (Open Systems Interconnection)
    - developed by ISO
    - 7 layers
    - theorectical system delivered too late
    - TCP/IP is fact standard
  - TCP/IP
    - key protocols were mature and well tested while similar OSI protocols were in development
    - OSI model is uncessarily complex

![TCP/IP Applications](./tcp_ip_applications.png)

- Model used in thise course

  - based on TCP/IP model
  - Layers
    - Application
    - Transport
    - Network
    - Link
    - Physical

# Physical Layer

- communication in forms of electromagnetic waves

  - media types

    - guided media
      - twisted pair, coaxial cable, optical fiber
    - unguided media / wireless
      - propagation through air, vaccum, seawater

  - Types of Links
    - Direct link
      - transmission path between two devices
      - no intermedia devices
        - except for amplifier or repeaters used to increase signal strength
      - both guided and unguided media
    - Point-to-Point
      - direct link between devices
      - are only 2 devices sharing medium
    - Multi-point
      - more than 2 devices share same medium
  - Transmission directions
    - Simplex
      - signals transmitted in only one direction
      - one transmitter and receiver
      - ex: TV / Radio
    - Half duplex
      - both stations can transmit but at only one time
      - ex: walkie talkie
    - Full duplex
      - both stations may transmit simulatenously
      - medium carrys signals in btoh directions at the same time
      - ex: telephones

- Analog and Digital Signals
  - electromagnetic signals are function over time
  - Time, domain, X axis
    - Analog signal
      - signal intensity varies in smooth fashion over time
        - no breaks or discontinuity
    - Digital signal
      - signal intensity maintains a constant level for some period of time
        and then abruptly changes to another
        - discrete
    - Periodic signals
      - same signal pattern repeated over time
      - ex: sine wave, square wave
  - Sine wave
    - fundamental periodic signal
    - 3 parameters
      - Peak amplitude (A)
        - max value or strength of signal over time
        - usually in volts
      - Frequency (f)
        - Rate at which signal repeats
        - Heartz(Hz) / cycles per second
        - Period (T) is amount of time for one reptition
          - T = 1 / f
          - inversely proportional to frequency
        - Phase
          - relative position in time within a signal period of a signal
    - equation
      <img src="https://latex.codecogs.com/gif.latex?s(T)&space;=&space;A&space;\sin(2\pi&space;ft&space;&plus;&space;\varphi&space;)" title="s(T) = A \sin(2\pi ft + \varphi )" />
    - Wavelength (lambda)
      - wavelength of a signal is distance occupied by a signle cycle
        - distance between two consecutive cycles
      - <img src="https://latex.codecogs.com/gif.latex?\lambda&space;=&space;v&space;T" title="\lambda = v T" />
      - <img src="https://latex.codecogs.com/gif.latex?\lambda&space;f&space;=&space;v" title="\lambda f = v" />
      - when v = c
        - c = 3x10^8 m/s (speed of light in free space)
    - Frequencies
      - signals made of many frequencies
      - components are sine waves
      - Fourier analysis shows that any signal is made up of components at various frequencies in which each component is a sinusoid
      - can plot frequency domain functions
- Spectrum and Bandwidth

  - spectrum of a signal
    - range of frequencies contained in signal
  - absolute bandwidth of signal
    - width of spectrum
      - ex: 3f - 1f = 2f
  - effective bandwidth / bandwidth
    - narrow band of frequencies containing most energy
  - Dc Component
    - component of zero frequency
  - given waveform may contain frequencies over very broad range
    - any **transmission system** has a limited band of frequencies
      - limits data rate that can be carried on transmission medium
  - Bandwidth is physical property of transmission medium
    - construction, sickness, and length of wire/fiber
  - <img src="https://latex.codecogs.com/gif.latex?\theta&space;_{n}&space;=&space;tan^{-1}(-B_{n}/A_{n})" title="\theta _{n} = tan^{-1}(-B_{n}/A_{n})" />
  - Having less bandwidth (harmonics) degrades signal
    - more difficult to retrieve the information
    - 1 harmonic is the fundamentally frequency of original signal

- Fourier Analysis

  - Theorem
    <img src="https://latex.codecogs.com/gif.latex?x(t)&space;=&space;(A_{0}/2)&space;&plus;&space;\sum_{n=1}^{\infty}&space;A_{n}\cos(2&space;\pi&space;n&space;f_{0}&space;t)&space;&plus;&space;B_{n}\sin(2&space;\pi&space;n&space;f_{0}&space;t)" title="x(t) = (A_{0}/2) + \sum_{n=1}^{\infty} A_{n}\cos(2 \pi n f_{0} t) + B_{n}\sin(2 \pi n f_{0} t)" />
    - f0 = 1/T, fundamnetal frequency
      <br>
      <img src="https://latex.codecogs.com/gif.latex?A_{0}&space;=&space;\frac{2}{T}&space;\int_{0}^{T}&space;x(t)&space;dt" title="A_{0} = \frac{2}{T} \int_{0}^{T} x(t) dt" />
      <br>
      <img src="https://latex.codecogs.com/gif.latex?A_{n}&space;=&space;\frac{2}{T}&space;\int_{0}^{T}&space;x(t)\cos(2&space;\pi&space;n&space;f_{0}&space;t)&space;dt" title="A_{n} = \frac{2}{T} \int_{0}^{T} x(t)\cos(2 \pi n f_{0} t) dt" />
      <br>
      <img src="https://latex.codecogs.com/gif.latex?B_{n}&space;=&space;\frac{2}{T}&space;\int_{0}^{T}&space;x(t)\sin(2&space;\pi&space;n&space;f_{0}&space;t)&space;dt" title="B_{n} = \frac{2}{T} \int_{0}^{T} x(t)\sin(2 \pi n f_{0} t) dt" />
  - Amplitude phase
    <img src="https://latex.codecogs.com/gif.latex?x(t)&space;=&space;C_{0}&space;&plus;&space;\sum_{n=1}^{\infty&space;}&space;C_{n}\cos(2&space;\pi&space;n&space;f_{0}t&space;&plus;&space;\theta&space;_{n})" title="x(t) = C_{0} + \sum_{n=1}^{\infty } C_{n}\cos(2 \pi n f_{0}t + \theta _{n})" />
    <br>
    <img src="https://latex.codecogs.com/gif.latex?C_{0}&space;=&space;A_{0}&space;/&space;2" title="C_{0} = A_{0} / 2" />
    <br>
    <img src="https://latex.codecogs.com/gif.latex?C_{n}&space;=&space;\sqrt{A_{n}^{2}&space;&plus;&space;B_{n}^{2}}" title="C_{n} = \sqrt{A_{n}^{2} + B_{n}^{2}}" />
    <br>
    <img src="https://latex.codecogs.com/gif.latex?\theta&space;_{n}&space;=&space;tan^{-1}(-B_{n}/A_{n})" title="\theta _{n} = tan^{-1}(-B_{n}/A_{n})" />
  - For periodic signal, Average power in one signal
    <img src="https://latex.codecogs.com/gif.latex?P&space;=&space;\frac{1}{T}&space;\int_{0}^{T}&space;\left&space;|&space;x(t)&space;\right&space;|^{2}dt" title="P = \frac{1}{T} \int_{0}^{T} \left | x(t) \right |^{2}dt" />
  - Distribution of power
    <img src="https://latex.codecogs.com/gif.latex?S(f)&space;=&space;\sum_{n=-\infty}^{&plus;\infty}&space;\left&space;|&space;C_{n}&space;\right&space;|&space;^{2}&space;\delta&space;(f&space;-&space;n&space;f_{0})" title="S(f) = \sum_{n=-\infty}^{+\infty} \left | C_{n} \right | ^{2} \delta (f - n f_{0})" />
    - integral of delta(f)dt = 1
    - delta(f) is unit impulse
      - if f = 0, infinity
      - if f != 0, 0
    - Cn is fuourier coefficients
  - periodic waveform, power through first j harmonics is
    <img src="https://latex.codecogs.com/gif.latex?P&space;=&space;\frac{1}{4}C_{0}^{2}&space;&plus;&space;\frac{1}{2}&space;\sum_{n=1}^{j}&space;C_{n}^{2}" title="P = \frac{1}{4}C_{0}^{2} + \frac{1}{2} \sum_{n=1}^{j} C_{n}^{2}" />
    - half-power bandwidth is interval between frequencies which S(f) has dropped to half of its max value of power, called: 3dB point
    - Numbers of harmonics passed
      - Bandwidth / (bit rate in bps / bit-data)

- Nyquist formula

  - Nyquist Sampling Theorem
    - frequency of signal is no higher than B Hz
      - a sufficient sample-rate is anything larger than 2B samples per second
    - Maximum possible data rate on communication channel
      - data rate - in bits per second (bps)
      - bandwidth - cycles per second or hertz
      - noise - on communication link
      - error rate - corrupted bits
    - Limitations due to physical properties
      - greater the bandwidth the greater the cost
    - High of data rate at particular limit of error rate at given bandwidth
      - main constraint: noise
    - low for 0, high for 1
    - Symbol: consists of signal bits or n bits
      - 2 symbols: 0 and 1
      - 4 symbols: 00, 01, 10, 11
      - **M = 2^n**
        - M - symbols
        - n - bits
    - in noise-free channel, rate of signal transmission is 2B then signal with frequencies nog reater than B
      - given bandwidth B, highest signal rate is 2B
    - In noise-free channel
      - **Max data rate = 2B log2 M bits/sec**
        - B is bandwidth in Hz
        - M is number of signal levels
  - ex 1

    - noiseless chanlle with bandwidth of 3000 Hz, transmit signal with two signal levels, what is max bit rate?

    - BitRate = 2 x 3000 x log2 2 = 6000 bps

  - ex 2
    - need to send 265 kbps over noisless channel with bandwidth of 20 kHz, how many signals?
    - 265000 = 2 x 20,000 log2 L
    - log2 L = 6.625
    - L = 2^6.625 = 98.7 since not an int, we need to **increase levels or reduce bit rate**
      - increase level: L = 2^7, bit rate = 2 x 20,000 log2 128 = 280kbps
      - decrease level: L = 2^6, 240 kbps
      - **choice in this case, reduce bit rate**

- Shannon Capacity Formula
  - relation
    - components
      - data rate
      - noise
      - error rate
    - burst of noise affects more bits
    - given noise level, higher rates = higher errors
  - Forumla
    <img src="https://latex.codecogs.com/gif.latex?Max.data.rate&space;=&space;B&space;log_{2}(1&plus;&space;\frac{S}{N})&space;bits/sec" title="Max.data.rate = B log_{2}(1+ \frac{S}{N}) bits/sec" />
    - B = bandwidth (hz)
    - S = signal strength
    - N = noise
  - Capacity
    <img src="https://latex.codecogs.com/gif.latex?C&space;=&space;B&space;log_{2}(1&space;&plus;&space;SNR)" title="C = B log_{2}(1 + SNR)" />
  - theoreticla maximum capacity
  - get lower in practice
  - SNR, signal-to-noise ratio
    - decibels
    - SNRdb = 10Log10(S/N)
  - Ex
    - channel, 1-MHz bandwidth, SNR is 63
      - What is appropriate bit rate and signal level?
        - shannon formula to find upper-limit
          - 10^6 log2(1+63) = 6
        - Nyquist
          - 6 Mbps = 2x1MHzxlog2 L => 2^3
        - for better performance, choose lower bit rate
          - ex: 4 Mbps
  - Shannon capacity gives upper limit
  - Nyquist forumla tells us how many levels
    - tells use achievable max data rate
    - must use different signaling method to achieve Shannon's
- Analogy and Digital Data Transmission
  - continuous and discrete
  - 3 regards
    - data
      - entities that convey info
    - Signals
      - electric or electromagnetic representation of data
    - signaling
      - physical propagation of signal along suitable medium
    - transmission
      - communication of data by propgation and processing of signals
  - Analog Data
    - audio, accoustic soundwaves
    - humans can perceive it
    - human speech
      - 100hz to 7 kilo hz
      - 600-700 hz usually
      - 25db
    - limited frequency spectrum, continuous
  - Digital Data
    - binary data
    - terminals, computers
    - two DC components
      - 0 and 1
    - bandwidth depends on data rate
    - digital to modem to analog signal
      - encodes data
    - analog to codec to digital signal
      - decodes data
    - Pros and cons
      - cheaper
      - data integrity
        - less susceptible to noise
          - repeaters -> longer distance over lower quality lines
      - but greater attenuation
        - can lose info
- Transmission Impairments
  - Signal received may differ from transmitted
    - Analog, degradation of signal quality
    - Digital, bit errors
  - Most significant impairments
    - attenuation distortion
    - delay distortion
    - noise
  - Attenuation
    - strength of signal fall off with distance over any transmission medium
      - varies with frequency
      - higher frequency = greater attenuation
  - Delay Distoration
    - occur in transmission cable
      - twisted pair, coaxial cable, optic fiber
    - doesn't occur in air
      - antennas
    - Occurs because propgation speed of signal in guided medium varies with frequency
      - velocity tend to be highest at center, fall off at ends
        - arrive different times, varying delays
    - critical for digital data
      - part of one bit spill over into others
        - **intersymbol interference**
          - limitation to max bit rate
  - Noise
    - additional signal between transmitter and receiver
    - categories
      - thermal (predictable and constant magnitude)
        - thermal agitation of electrons
        - presented in all elecontric devices and medias
        - uniformly distributed across bandwidths
        - white noise
      - Intermodulation (predictable and constant magnitude)
        - by nonlinerarities in transmitter and receiver
        - effect to product signals at frequency that is sum or difference of two original frequencies
        - due to excessive signal strength / malfunction
      - Impulse noise (non continuous)
        - irregular pulse or spike
        - short duration
        - high amplitude
        - minor annoyance for analog signals
        - major source of error in digital data
        - lightning, etc
  - Succeesful transmission of data based on
    - quality of signal
    - transmission medium characteristics
  - Characteristic and quality determined by medium and signal
    - unguided media
      - bandwidth produced by antenna
    - guided media
      - medium
- Circuit Switching
  - uses dedicated path betweeen two stations.
  - three phases
    - establish
    - transfer
    - disconnect
  - inefficeint
    - channel capacity dedicated for duration of connection
    - if no data, capacity wasted
  - setup connection takes time
    - transfer is transparent after connected
  - telephone system
    - fully-interconnected network
      - all nodes connected with each other
    - centralized switch
      - simplicity
      - short hub, 1 node in center
      - security, maintability, upgradability
      - reliability, failure in center, entire network damaged
    - two-level hierarchy
      - combination of full-interconnected and centralized switch
      - star configuration
    - components
      - local loops
        - analog twisted pairs into houses and businesses
      - trunks
        - digital fiber optics connecting the switching offices
      - switching offices
        - pass calls from one trunk to another
      - local call procedure
        - direct connection made when subscribe attached to given end office calls another subscribe to the same end office
      - long distance calls
        - outside local loop
          - end office routes call to nearby toll office
        - caller and callee phone in same toll office
          - direct connection
        - caller and callee phone different toll offices
          - additional hierarchy required, intertoll trunk (interoffice-trunk)
    - Local loop
      - modem to convert digital to analog
      - codec to convert analog to digital
      - computer -> digital -> modem -> analog -> codec -> digital -> medium-bandwidth trunk -> toll office -> high-bandwidth trunk-> toll office
- SONET

  - protocol to transfer bit to bit string
  - sychnonous optical entwork
  - high speec capability of optical fiber
  - virtually all long-distance telephone use SONET in pyhsical layer
  - common signal standard
    - wavelength, timing, framing structure
    - possible for different carrier interwork
      - unify US, Euro, Japanese digital systems
    - can multiplex multiple digital channels

  # Packet Switching

  - circuit-switched
    - **for voice**
    - dedicated path established
      - fiber, copper, microwave
  - Packet-switched

    - **for data**
    - small packets
    - **consists of data and control info**
      - routing info
    - received, stored briefly, passed onto next node
    - typical upperbound, 1000 bytes

      - series of packets if larger

    - pros
      - line efficiency
        - single node-to-node link can be dynamically shared by many packets over time
        - packets are queued up and transmitted as possible over the link
      - Data rate conversion
        - two stations of diff data rate can exchange packets
          - can buffer data if require equalize data rates
      - Packets accepted even when network is busy
        - still accepted but delivery delay increases
      - Priorities can be used
    - Techniques
      - stations break long msg into packets
      - packets sent one at a time to network
      - packets handled in two ways
        - datagram
          - each packet is treated independently with no reference to previous packets
        - virtual circuit
          - preplanned route is established before any packets are sent

- datagram

  - **packets may not follow same route** despite same destination
    - depends on traffic, availability, etc
    - **may not be in order**
  - **Destination must reorder**
    - handle lost of packets
    - packet can be destroyed
      - crashed

- Virtual circuit diagram

  - route is fixed prior to data transfer
  - Node doesn't have to make routing decision for packet
    - only has to do it once

- Packet Size vs Transmission Time

  - T = (data length + header size) x (# of hops + (packets per msg - 1))
    - msg: 40B octects/bytes, 3B control info
    - X -> a -> b -> Y
      - entire msg: (40 + 3) x 3 = 129BT, 3B overhead
      - 2 messages: (20 + 3) x (3 + (2 - 1)) = 92BT, 6B overhead
      - 5 message: (8 + 3) x (3 + (5-1)) = 77BT, 15B overhead
    - **processing, queueing at each node, and overhead for more packets**

- Timing

  - Host -> Node1 -> node2 -> host2
  - Delays
    - propagation
      - signal propagate from one node to next
      - usually neglible
    - Transmission
      - transmitter send out block of data
    - Node
      - processing
  - Circuit

    - circuit establish
    - data transmission
    - circuit terminated
    - Total Delay = total transmission + total propgation + total processing

      - Linked prpagation delay = L
      - per-hop processing delay = P
      - transmission speed = W bit/s
      - message size = B bits
      - number of hops = M

      - Total transmission = B / W
      - total propgation = 4ML
        - link to host, return back link, send data, terminate link
      - total procesing = (M-1)P
      - B/W + 4ML + (M-1)P

  - Datagram

    - packet transmission delay = T
    - message contains N packets
    - total delay = total transmission + propagation + processing + store&forward
      - NT + ML + (M-1)P + (M-1)T

  - Virtual circuit packet switching
    - total delay
    - NT + (M-1)T + 4ML + 4(M-1)P
  - **interested in only delay elapsed from first bit sent to last bit was received**
    - circuit swithcing
      - delay= B/W + 3ML+ (M-1)P
    - datagram packet switching
      - delay = NT + ML + (M-1)T + (M-1)P
    - virtual circuit packet switching
      - delay = NT + 3ML + (M-1)T + 3(M-1)P

- Slow link vs Fast link
  - propgation bounded by speed of light
  - circuit switching
    - adds exrtra dountrip over packet switching
    - eliminates sotre and forward delays
  - Slow link
    - bottleneck is transmission speed
      - w/o store and forward helps a lot
      - extra roundtrip adds neglible delay
      - circuit switching
  - Fast link
    - bottleneck is propagation delay
      - adding roundtrip hurts
      - w/o store and forward saves neglible amount of time
      - packet switching

# Modem, Ecoding, Modulation

- Modem

  - input: accepts serial stream of bits
  - produces: modulated carrier and vice versa
    - twisted pair, cable, optical fiber, wireless
  - AC signaling used
  - sine wave carrier

- Signal Encoding Techniques

  - digital signaliing g(t) encoded (encoder) into digital signal x(t)

    - x(t) depends on encoding technique and optiimze transmission medium

  - Modulator
    - Carrier frequency
      - continuous constant-freq signal
        - basis for analog signaliing
      - data transmitted using carrier signal by modulation
        - Modulation
          - process of encoding source data into carrier signal with freq Fc
          - all modulation techniques
            - involve one or more
              - amplitude, frequency, phase
          - Input signal m(t), modulating signal / baseband signal
            - digital or analogy
          - output: modulated signal s(t)
            - s(t) is bandlimited (bandpass) signal
          - location of bandwidth on spectrum is related to fc, often near center
          - actual encoding, chosen to optimize characteristic of transmission

# Digital Data vs Digital Signal

- Digital Signal
  - seq of discrete, discontinuous voltage pulses
  - each pulse is a signal element
  - binary data transmitted, each bit into signal element
- Terminology
  - unipolar
    - all signal elements have same sign
  - polar
    - one logic state represented by positive and negative voltage
  - Data rate
    - rate of data, bits per sec (bps)
  - Duration or lenght of bit
    - time taken for transmitter to emit for the bit
  - Modulation rate
    - rate at whcih signal level is changed
    - rate expressed in baud, signal elements per second
  - Mark and Space
    - binary digits: 1 and 0 respectively

# Key Data Transmission Terms

- Data Element
  - Bits
  - signla binary, 0 or 1
- Data Rate
  - bits per sec (bps)
- Signal element
  - digital: voltage pulse of constant amplitude
  - analog: constant freq, phase, and amplitude
  - part of signal that occupies shortest interval of signaling code
- Sigal rate / modulation rate
  - signal elements per second (baud)
  - rate of signal elements transmitted

# Encoding Schemes

- Nonreturn to Zero-Levle (NRZ-L)
  - 0 = high, 1 = low
- Nonreturn to Zero Inverted (NRZI)
  - 0 = no transition at beginning of interval (one bit time)
  - 1 = transition at beginning of interval
- Bipolar-AMI
  - 0 = no line signal
  - 1 = postiive or negative, alternative for successive ones
- Pseudoternary
  - 0 = positive or negative level, alternative successive zeros
  - 1 = no line signal
- Manchester
  - 0 = transition high to lower, in middle of interval
  - 1 = transition from low to high, in middle of interval
- Differential Manchester
  - always transition in middle of interval
  - 0 = transition at beginning of interval
  - 1 = no transition at beginning of interval
- B8ZS
  - same as bipolar AMI, except any string of 8 zeros is replaced by a string of two code violations
- HDB3
  - same as bipolar AMI, except any string of four zeros if replaced by one code violation

![Encoding Schemes](./encoding.png)

- sequence: 01001100011

- Nonreturn to Zero-Level (NRZ-L)
  - voltage constant during bit interval
  - no transition, no return to zero voltage
  - negative voltage for one value, positive for other
- Nonreturn to Zero Inverted
  - inverted on ones
  - data encoded as presence or absence of signal transition
    - transition (low to high or high to low ) = 1
    - no transition = 0
  - differential encoding
    - more reliable to detect transition with noise
    - easy to load the sense of the polarity
- NRZ pros and cons

  - pros
    - easy to engineer
    - good use of bandwidth
  - Cons
    - dc component
    - lack of synchonization capability
  - Used for megnetic recording
  - not often used for signal transmission

- Multilevel Binary Polary AMI (alternate mark inversion)

  - more than 2 levels
  - Bipolary AMI
    - alternate one pulses in polarity
    - pros
      - no loss of sync if long string of ones
      - long string of zersos still a problem
      - no net dc component
      - lower bandwidth
      - esy error detection
  - Pseudoternary

    - one is absennce of line signal
    - zero represented by alternating positive and negative
    - no advantage or disadvantage over bipolar-ami

- Manchester and Differential Manchester
  - Manchester
    - transition in middle of each bit period
    - transition is clocking mechanism and as data
    - low to high = 1
    - high to low = 0
  - Differential
    - midbit transition is clocking only
    - transition at start of bit period representing 0
    - no transition at start of bit period representing 1
  - Biphase pros and cons
    - Con
      - at least 1 transition per bit time and possibly two
      - maxium modulation rate is twice NRZ
      - requires more bandwidth
    - Pros
      - synchonization on mid bit transition (self clocking)
      - no dc component
      - error detection
  - data transmission
    - manchester, ethenet standard, coxial and twisted pair
    - differential, token relap
    - LAN applications, 10 MB/s
- Scambling
  - scambling to replace sequences that produce constant voltage
  - filling sequences
    - produce enough transitions to sync
    - be recognized by received and replaced with original
    - same length as original
  - design goals
    - no dc component
    - no long seqs of zero level line signal
    - no reduction in data rate
    - error dection capability
  - **B8ZS Scrambler**
    - 8-zeros substitution
    - coding scheme commonly used in North America
    - based on bipolar-AMI
      - if octect all zeros, last voltage pulse preceding is positive
        - then octect = 000+\-0\-+
      - if octect all zeros, last voltage pulse preceding is negative
        - then octect = 000\-+0+\-
- Baud Rate
  - data rate = 1 / Tb (Tb, bit duration, bits per seconds)
  - Modulation rate, signal which signal elements are generated
    - Manchester
      - max baud = 2 / Tb
- Digital Data, Analog Signal

  - public telephone system
    - 300Hz to 3400Hz freq range
    - modem, (modulator-demodulator)
  - Encoding techniques
    - ASK, amplitude shift keying
      - encode 0/1 by different carrier amplitudes
        - one amplitude for zero
      - susceptible to sudden gain changes
      - Uses
        - 1200 bps for on-voice grade lines
        - very high speeds over optical fibers
    - FSK
      - Binary FSK (BFSK)
        - two values represented by two different frequences (near carrier)
        - less suscepitable to error than ASK
        - uses
          - 1200bps on voice grade lines
          - high frequency )3 - 30 MHz) radio
          - evenhigher freq on LAN using coaxial cable
    - PK
      - Phase shift keying (PSK)
        - phase signal represent data
        - binary PSK (two phases)
      - Different PSK (DPSK)
        - shifted relative to previous transmission rather than some reference signal
        - 1 = oposite signal of previous one
      - Quadrature PSK (QPSK)
        - shifts of pi/2 (90 deg)
          - starts at 45 deg
          - 00, 01 (-x), 11 (-x, -y), 10 (-y)
        - each element represents two bits
        - split input data stream into two
          - module onto carrier
          - phase shifted carrier
      - QAM, constellation diagrams
        - QPSK
        - QAM-16
        - QAM-64
      - Gray coded
        - symbols represent bits
        - 1 bit difference to neighbors

- Analog Data Digital Signal

  - digitization: conversion of analog data into digital data
    - transmitted using NRZ-L
    - using code other than NRZ-L
    - converted to analog signal
  - Codec
    - analog to digital conversion
    - delta modulation
  - Analog data (voice) -> digitizer -> digital data -> modulator -> analog signal

- Pulse Code Modulation (PCM)
  - if signal sampled at regular intervals at higher rate than highest signal frequency (Nyquisty) then sample contains all info of original signla.
    - can reconstruct using lowpass filter
    - 4000Hz voice data, requires 8000 sample per sec
- Pule Amplitude Modulation (PAM)

  - analog samples
  - convert to digital, each analog samples must be assigned a binary code

- PCM Block Diagarm
  - continuous time / amplitude (analog input signla)
  - PAM Sampler
  - discrete time continous amplitude signal (PAM pulses)
  - Quantizier
  - discrete time discrete amplitude signal PCM pulses
  - Encoder
  - digital bit stream output signal
- Non-linear coding
  - mean absolute error same for each level
  - 24-30db achieved
- Delta Modulation (DM)
  - analog input is approx by staircase function
  - move up or down one quantization level at each sampling interval
  - Binary behavior
    - each sampling time, function moves up or down constant amount
    - output of delta modulation process represented as single binary digit for each sample
  - bit stream is produced by approx the derivative of analog signal rather than it's amplitude
    - 1 generated if staircase function goes up, else 0 goes down
- Multiplexing
  - utilize data link under heavy load
  - multiple channels on 1 phyiscal link
  - common on long-haul, high capacity links
  - n inputs, mux, 1 link, n channels, demux, n outputs
  - telephone companies developed schemes for multiplexing conversations over single physical trunk
  - Frequency division multiplexing (FDM)
    - video / tv set
    - freq spectrum is divided in frequency bands
    - each user has exclusive possession of a freq band
    - useful bandwidth of transmission medium exceeds required bandwidth of signals to be transmitted
    - number of signals carried simultanoueously
      - moduled onto diff carrier frequency
      - carrier frequencies sufficiently separated
    - coaxial and microwave systems
    - digital or analog signals
  - Time division Multiplexing (TDM)
    - digitized voice and data streams
    - use round-robin with each user getting entire bandwidth of a short time period
    - telephone and celluar systems
  - Wavelength division multiplexing
    - another name for FDM
      - used to carry many signals on one fiber
      - multiple beams of light at diff frequencies
      - carried over optical fiber links

# Datalink Layer

- Datalink Layer
  - responsible for delivering frames of information over a single link
    - regulates flow of data, bits in same order
    - handle transmission errors
  - Services
    - framing
    - control flow
    - flow control
  - provide well-edfined service interface to the network layer
  - dealing with transmission errors
  - regulating the flow of data so that slow receivers not swamped by fast senders
  - Packet from network layer into Frames
    - Frames have a header, payload field, trailer
- Services provided to Network Layer

  - principal service
    - transfer data from network layer on source machine to network layer on destination machine
  - services
    - unacknowledged connectionless service
      - src send frames to dest, dest no acknowledge of reception
      - no logical connection between src and dest
      - no action for lost frames
      - appropriate for low error rate transmission
        - recovery left to higher layers
      - appropriate for real-tiem traffic
    - acknolwedged connectionless service
      - no logical connection between source and destination
      - each frame individually acknowledged
        - timed monitoring
      - commonly used in wireless networks (WIFI)
    - acknowledged connection-oriented service
      - source and destination establish connection before data transmitted
      - frames are numbered
      - each frame received exactly once and in correct order
      - appropriate for long, unreliable links
        - satellite channel / long-distance telephone circuit
  - Phases for connection-oriented service
    - connections established between sender and receiver
      - init frame tracking (buffers and counters)
    - frame transmission
    - connection is released

- Framing

  - error dection in data link layer
    - break bit stream into frames, determine checksum for each frame at source
    - checksum is recomputed after frame is received at destination
    - error detection is based on comparing check sums
      - if not same, then error has happened
  - Breaking bit streams into frames
    - byte count
    - flag bytes with byte stuffing
    - bit stufing with starting and ending flags
    - physical layer coding violations

- Byte count
  - header field specifies number of bytes in frame
  - byte count to determine end of frame
  - erors amke frame tracking difficult
    - doesn't know where next frame should start
  - rarely used by itself
- Flag Bytes with Byte Stuffing
  - each frame starts and ends with special bytes
    - flag bytes
    - 2 consecutive flag bytes indicate end of one frame and start of next
  - provide resynchonization after errors
  - Byte stuffing
    - send data link layer insert special escape byte (ESC) before flag bytes that are encoded as part of the data
    - detect and remove escape byte prior to passing data
    - **Rules**
      - if flag byte, add escape byte before it
      - if escape byte, add escape byte before it
      - if escape then flag byte then need to escape both, thus 2 more escape bytes added
      - if escape byte then escape then need to escape both, thus 2 more escape bytes added
- Bitstuffing with starting and ending flags
  - bit pattern of flag byte: 0x7E
  - if data contains flag pattern: 01111110
    - on transmite, after 5 1's in data, 0 is added
    - on receive, a 0 after 5 1's is deleted
  - USB uses bit stuffing
  - Physical layer coding violations
    - used only for networks where encoding on physical medium contains some redundancy
      - some signals will not occur in regular data
      - some reserved signals to indicate start and end frames
      - usign coding violations to delimit frames
      - ethernet and wifi
        - might have a preamble
- Error Control and FLow Control
  - acknowledgement protocol, special control frames
    - positive, frame arrived safely
    - negative, something gone wrong, must be transmitted again
  - Timers to accomodate missing frames
    - uses connection-oriented appraoch
    - avoid hanging for response
    - **use sequence numbers** to differeniate originals from retransmissions
  - Flow Control
    - sender transmits frames faster than receiver can accept
    - approaches
      - feedback-base flow control
        - receiver send back info
          - permission to send more data
          - status of receiver
      - rate-based flow control
        - limit rate of which senders may transmit data, without feedback from receiver
        - can send N frames until X time to send more.
- Error Detection and Correction

  - error correction is ongoing problem
    - really bad for wireless
  - use redundant info
    - error correcting codes
      - decipher data transmitted
      - correction codes
      - FEC
    - error detecting codes
      - deicpher error has occurred
      - request retransmission
  - Bit Error Rate
    - probability of bit received is incorrect
    - frame of F bits long
    - Probability (frame arrives perfectly)
      - (1 - Pb)^F
    - Probability(error detection, error go undetected)
      - 1 - P(frame arrives perfectly)
  - Regardless of transmission medium
    - There will always be errors

- Error Correction

  - Codeword
    - n-but unit (frame) containg data (m bits) and redundant check bits (r bits)
    - (n, m) code
      - n = m + r
    - XOR to find num bits differ, count num 1 bits
      - **hamming distance**: number of bit positions differ
      - d single-bit errors to convert one into the other
  - Construct list of legal codewords
    - message can have 2^ possibilities
    - bit calculation limits codeword possibilities (< 2^n)
    - r check bits
      - legal codewords in possible messages: 2^m/2^n or 1/2^r
  - Depend on hamming distance
    - **detect d errors, need d + 1 code**
    - to correct d errors, need distance 2d+1 code
  - ex: code distance = 5
    - error correction = 2d+1 = 5, d = 2
    - error detection = d + 1 = 5, d = 4
  - Parity bit
    - number of 1 bits in codeword is even or odd
      - modulo 2 sum
      - or XOR of data bits
    - append parity bit to end of word
    - even parity
      - ensure even number of 1's
        - add 1 or 0 depending on situation
    - odd parity
      - ensure odd number of 1's
        - add 1 or 0 depending on situation
    - Code with single parity bit has **hamming distnace of 2 of the complete code**
  - Lower limit
    - design code with n bits = n = (m + r)
    - 2^m legal messages, n illegal codewards at distance 1
    - 2^m messages require n+1 bit patterns
    - total number of bit pattern is 2^n

- Hamming Code (Left to right of the bits)

  - bits of powers of 2, include 2^0 are are check bits (r bits)
    - bits 1, 2, 4, 8, 16
  - remianing bits contain data (m bits)

    - bits 3, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15, etc

  - Check bit utilization
    - check bit used to set parity bit of collection of bits
    - k = 11, 11 = 1 + 2 + 8
    - k = 29, 29 = 1 + 4 + 8 + 16
    - 8-bit binary value, 10011010, even parity
      - 8 bits of binary data, (1, 2, 4, 8 are reserved for check bits)
      - 1_2_3_4_5_6_7_8_9_10_11_12
      - _\_\_\_1_\_\_0*0_1*\_\_1*0*\_1\_\_0
      -     (1+2), (1+4), (2+4), (1+2+4), (1+8), (2+8), (1+2+8), (4+8)
      - calculate parity bits (using the parity scheme)
        - 1 (find all those data bits with 1+..)
        - 2 (find all those data bits with 2+..)
        - 4
        - 8
  - Error Detection process
    - receiver initializes counter to 0 when codeword arrives
    - examine each check bit k (k = 1, 2, 4, 8, etc) for correct parity
    - receiver adds k to counter if incorrect parity found
      - if counter = 0, accept codeword
      - if counter != 0, counter contains number of incorrect bits
      - counter sum indicates location of incorrect bit
        - if check bits 1, 2, 8 are error, 11 is inverted bit
      - **resolving after finding errors**
        - once bits detected
          - original codeword = invert the errorbits in the received code
          - original message = all the non-check bits concatenated

- Burst Error Correction using Hamming Code

  - burst error correction
    - arrange sequence of k consecutive codewords in matrix with one codeword per row
    - transmit data one columne at a time starting with leftmost column
    - begin transmission of next column after current column is completed
    - reconstruct matrix of k transmitted codewords from the columns received
    - if burst error of length k occurs, at most 1 bit in each of k codeword is affected
      - restore block based on hamming code correct of on error per codeword
  - example

- Burst Detection

  - Parity bit approach
    - represent block to be transmitted as matrix of k rows x n bit columns
      - compute parity bit for each column
      - append parity bits as final row of matrix
  - receiver checks all parity bits received
  - block retransmission is performed for any parity bit errors detected

- Using polynomial codes

  - also known as Cyclic Redundancy Check (CRC)
  - Polynomial codes represent bit strings as polynomials with coefficients of 1 and 0
  - k-bit frame as a coefficient list for polnomial with terms x^0 ... x^k-1
  - x^5+x^2+x^0 = 100101
  - Polynomial arithmetic
    - performed using modulo 2
    - no carries for addition or borrows for subtraction
    - ex:
      - 10000011 + 11001010 = 01001001
  - agree on polynomial generate G(x)
  - high and low order bits of generator must be 1
  - compute checksum for m-bit frame with polynomial representation M(x)
  - frame must be longer than polynomial generator

  - error detection
    - generate check sum polynomial for frame that is divible by G(x)
      - checksum appended to end of frame
  - procedure to compute checksum
    - messsage contain m bits
    - r denote degree of G(x)
    - append r zero bits to low order end of frame
      - x^r M(x)
    - divide bit string corresponding to x^r M(x) by string G(x) using modulo 2 division
    - subtract remainder (r bits or less) from bit string corresponding to x^r M(x) using modulo 2 subtraction
    - result represents checksummed frame used for transmission
  - ex:
    - m-bit msg = 1101011011
    - generator = 10011

- NEed to finish later

# Network Layer

## Review of TCP and IP

- TCP hand msg to IP with instructions to host B
  - IP hands msg to network access layer with instructions to router j
- enable data send across subnetwork
- IP in all end systems and router
- TCP only in end systems
- TCP keeps track of data to assure all delivered reliably to appropriate application
- Addressing requirements (2 levels)
  - each host on subnet needs unique global network address
    - IP address
  - each application on host needs unique address
    - port

## Connectionless Internetworking

- Internetworking involves connectionless operation at the level of the internet protocol
  - approaches
    - virtual circuit
    - datagram
  - initially dev for DARPA internet project
  - protocol is needed to access particular network
- must share same TCP and IP
- router only needs to implement up to IP
- connectionless internet facility is flexible
  - IP provides connectionless service between systems
    - flexible
    - made robust
    - no uncessary overhead

## IP Design Issues

- routing
  - ES/routers maintain routing table
    - indicate next router to which datagram is sent
    - static or dynamic
      - static can have alternative routes
      - dynamic more flexible for error and congestion conditions
  - source routing
    - seq list of routers to follow
    - useful for security and priority
  - Router recording
    - each router appends internet address to list of addresses in datagram
    - for testing and debugging
- datagram lifetime
  - if dynamic or alternate routing, then potential indefinite loop
    - consumes resources
    - transport protocol use upper bound lifetime
      - mark datagram with lifetime
      - discard when lifetime expires
      - Approaches
        - counter
        - time difference
          - can be used in reassmbly
- fragmentation and reassembly
  - data into smaller blocks, fragmentation
  - reasons
    - network accepts blocks of certain size
    - more efficient error control and smaller retransmission units
    - fairer access to shared facilities
    - smaller buffers
  - disadvantages
    - smaller buffers
    - more interrupts and time processing
  - When to re-assemble
    - at destination
      - packets get smaller as data traverses internet
    - intermediate re-assembly
      - need large buffers at routers
      - buffers may fill with fragments
      - all fragments must go through same router
  - IP fragmentation
    - IP re-assembles at destination only
    - user fields in header
      - Data Unit Identifier (ID)
        - identifies end system originated datagram
      - data length
        - length of user data in octects
      - offset
        - position of fragment of user data in original datagram
        - in multiples of 64 bits (8 octets)
      - more flag
        - indicate this is not the last fragment
    - Create datagram with data length = data field, offset = 0, more flag = 0 (false)
    - fragment into two pieces
      1. copy header fields of incoming datagram into two new datagrams
      2. divide data into two portions
      3. first data gram, set length to inserted data, more flag to 1, offset unchanged
      4. second data gram, set length to inserted data, length of first data portion / 8 to offset field, more flag unchanged
    - Approaches when to reassemble
      - reassembly lifetime
      - datagram lifetime
    - example
      - original IP datagram
        - IP header (20)
        - TCP header (20)
        - TCP Payload (384)
      - Partial 1
        - IP Header
        - TCP Header
        - Payload
      - Partial 2
        - IP Header
        - Payload
- error control
  - discarded datagram identification is needed
  - reasons
    - lifetime expiration
    - congestion
    - FCS error
- flow control
  - allow routers to limite rate of data received
  - send flow control packets requesting reduced data flow

## IP Parameters

- Source Address (IP Entity)
- Destination Address (IP Entity)
- Protocol
  - recipient protocol entity (IP user, such as TCP)
- Type of Service
  - specify treatment of data unit
- Identification
  - source address + destination address + user protocol
- Don't fragment identifier
  - whether IP can fragment data to accomplish delivery
- Time to Live
  - in seconds
- Data Length
- Option Data
- Data
- **identification, don't fragment identifier, and time to live** are present in send primitive, not deliver primitive

## IP Options

- future extensibility and inclusion of parameters not involved
- Values
  - Security
  - Source routing
  - Route recording
  - Stream identification
  - timestamping

## IPv4 IP Services

- IPv4
  - defined in RF 791
  - part of TCP/IP suite
  - two parts
    - Interface, specifying services IP provices
    - actual protocol format and mechanisms
- Primitives
  - specifies functions to be performed
  - form of primitive implementation dependent
    - like a procedural call
  - send-request transmission of data unit
  - deliver-notify user of arrival of data unit
- Paramters
  - used to pass data nd control info

## IPv4 Address Format

- classes
  - class A
    - 0, 7 bits network, 24 bits host
    - few networks
    - 127 reserved
      - range: 1-126
  - class B
    - 10, 14 bits network, 16 bits host
    - medium networks
    - 2^14 addresses
    - 128-191 reserved
  - class C
    - 110, 21 bits network, 8 bits host
    - many networks
    - 192-223 reserved
    - 2^21
  - class D
    - 1110, multicast
  - class E
    - 11110, future use
- IP Address
  - binary into numbers
  - Look at beginning bits to find which class it belongs to
- Subnets and masks
  - arbitrary complexity internetworked LANs within organization
  - insulate overall internet from growth of network numbers and routing complexity
  - site looks to rest of internet like single network
  - each LAN assigned subnet number
  - Host portion of address partitioned into subnet number and host number
  - local routers route within subnetted network
  - subnet mark indicates which bits are subnet numbers and which are host number

## Internet Control Message Protocol (ICMP)

- RFC 792
- provides means for transferring messages from routers and others hosts to a host
- provide feedback about problems
  - datagram can't reach destination
  - not buffer capacity forward
  - router can send traffic on shorter route
- Encapsulated in IP datagram
  - not reliable
- 64 bit header
  - type, code, checksum, pointer, 32 bit params, additional info fields
- common messages
  - destination unreachable, time exceeded, source quench
  - paramter problem
  - redirect
  - echo, echo reply
  - timestamp
  - timestamp reply
  - address mask request
  - address mask reply

## Address REsolution Protocol (ARP)

- MAC address to send to LAN host
  - included in network address
  - central directory
  - address resolution protocol
- ARP (RFC 826) provides dynamic IP to ethernet address mapping
  - source broadcasts ARP request
  - destination replies with ARP response

## Virtual Private Network (VNP)

- set of computers interconnected usign unsecure network
  - linking corporate LANs over Internet
- useing encryption and special protocols to provide security
  - evasdropping
  - entry point for unauthroized users
- Properietary solutions are problematic
  - development of IPSec standard

## IPsec

- RFC 1636 identified security need
- encyption and authentication necessary security features in IPv6
- designed also for use with current IPv4
- Benefits
  - provide strong security for external traffic
  - resistant to bypass
  - below transport layer henc transparent to applications
  - transparent to end users
  - can provide security for individual users if needed
- Functions
  - authentication header (AH)
    - for authentication only
  - Encapsulating Security Payload (ESP)
    - for combined authentication/encryption
  - key exchange function
    - manuagel or automated
  - VPNs usually need combined function

## Data Link Layer Protocols

### Elementary Data Link

- Elementary Data Link Protocols
  - unrestricted simplex
  - simplex stop-and-wait
  - simplex protocol for a noisy channel
- Assumption of model of communication
  - Physical, data link, network layers are independent processes that communicate by sending messages back and forth
  - Reliable, connection-oriented service is used for data stream transmission in one direction
  - Protocols address problems cause dby communication errors, not by machines crashing
  - Network layer provides only data across its interface to the data link layer
  - model
    - Computer
      - OS
        - Network
        - Driver
          - Link
    - hardware, network interface card (NIC)
      - Physical
      - Link
- Procedure for transmitter portion of communication model
  - data link layer encapsulates packet from network layer by inserting header and trailer
    - frame
      - packet
      - control info in header
      - check sum in trailer
    - Library procedures
      - to_physical_layer, send frames
      - from_physical_layer, receive frame
      - transmitting hardware computes and append checksum as trailer
- Procedure for receiver portion of the communication model
  - receiver in wait mode
  - receiver hardware computers checksum when frame arrives
    - acknowledges correct/incorrect transmission
    - frames resent until checksum is correct
  - receiver checks control info in header
  - receiver passes packet portion of frame to network layer
- Protocol definitions
  - boolean
  - seq_nr
  - packet
  - frame_kind
  - frame
    - kind （frame header)
    - seq (frame header)
    - ack (frame header)
    - info
  - wait_for_event
  - from_network_layer
  - to_network_layer
  - from_physical_layer
  - to_physical_layer
  - start_timer
  - stop_timer
  - stack_ack_timer
  - stop_ack_timer
  - enable_network_layer
  - disable_network_layer

### Utopia Protocol

- Unrestricted Simplex Protocol
- unidirectional data transmission
- transmitting and receiving network layers always ready
- processing time can be ignored
- infinite buffer space
- communication channel between data link layers never damages or lose frames

- sender and receiver

  - no seq or ack used, so no max seq needed
  - only frame's info is used

- **no flow control or error detection**

### Simplex Stop and Wait

- prevent sender from flooding receiver with data faster than receiver can process it
- assume
  - communication channel is assumed error free
  - reciever network layer has finite responses to incoming data
  - receiver data link layer has infinite buffer space
- possible solutions
  - build powerful receiver or insert delay into sender's physical layer
    - requires dedicate hardware
    - lose bandwidth
  - stop and wait approach
    - receiver provides dummy frame feedback (acknowledgement) to sender
      - give sender permission to send next frame
    - sender transmits frames based on acknowledgement from receiver
- data traffic is unidirectional
- frame transmission is bidirectional
- must have bidirectional information transfer
  - data flow alternation
    - sender sends a frame followed by receiver
- sends acknowledgement frame
