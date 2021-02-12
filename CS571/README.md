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
