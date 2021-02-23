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
