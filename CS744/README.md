# CS 744 Notes Software Project Managmement
## Agile Development
### Focuses
- coding, testing
- early releases (usable products)
- customer interaction
- adaptive to changes
- quality

### Prototyping life cycle vs Agile life cyle 
![lifecycles](./img/protoVSagile.png)
-  Right side is agile, **reqs can change**

### Examples (not important)
- XP, extreme programming
- FDD, feature driven development
- Dynamic Systems DEvelopment Method (DSDM)
- Lean Software Development 
- Kanban method
- Scrum

### Scrum Life Cycle 
![scrum](./img/scrum.png)
- Product Owner 
    - **creates reqs & maintain priority of reqs (product backlog)**
    - approves shippable product (acceptance criteria)
    - sprint planning, review, respective activities
    - Managerial responsibility
        - decision maker for funding 
        - ensure quality
    - knowledge of appl domain and vision of end product
    - **acts as the stakeholders** 
- Scrum Master (product/prj manager, but any other person could be)
    - coordinate all activites, work with product owner
    - resolve conflicts
    - coordinator

- Dev Team
    - experts, small (5-9)
    - scrum master provides technical assistance
    - sprint planning and prioritizing
    - goals, sprints, design, impl, integrate, test product
    - grooming backlog (revising and re-prioritizing)
    - self-organize, highly talented (T-shaped, use specialty, but try to help others)
    - good communication, focus on task

- Product Backlog 
    - features, changes, defect to be impl 
    - user stories form
    - **prioritized**
    - categorizable, can revise
    - size of PBI (item)
        - time to complete or # features 
    - Grooming
        - create, refine, estimate, reorder PBI's
    
- Sprint
    - cycle of seq activities 
    - fixed start and end dates (short, week - month)
    - no goal altering changes in scope, exceptions
    - shippable product delivered

- Sprint planning 
    - recurring, just-in-time at beg of each sprint
    - backlog item -> set of est tasks

- Sprint backlog
    - set of tasks impl product backlog items
    - **sprint backlog = "how impl", product backlog = "what to impl"**

- Cost Estimation
    - complex and rarely accurate
    - sprint task cost est based on programmers ability, current task, and available resources
        - time-boxed with fixed durations
        - **cost** is using estimated time for each sprint task


- Sprint Planning
    - product owner approve PBI's
    - scrum msater ensure resources avail
        - PBI can be impl within current spinrt too
    - dev team: designs, impl, tsts, integrate
        - acceptance tests and test cases

- Factors for estimated Tasks
    - complexity of PBI
    - team size
    - team capacity (skill/time)
    - available resources
    - business/technical constraints

- Sprint execution
    - impl and testing
    - dev team picks: work flow, tools & libs, internal test cases, design artifacts
    - Factors
        - accelerated programming: tools, libs, pair programming
        - code refactoring
            - refactor code should be small
            - comments & additional documentation
            - sketches/design diagrams
        - automated testing 
        - version control & code repos
        - time control (inteneral deadline diff)
    - Shippable product
        - complete but not yet integrated with rest, passed all tests

- Daily Scrum Meeting
    - product owner, scrum master, dev team
    - presents accomplished prev day and whats to be done
    - resolve conflicts

- No goal-altering changes during a sprint
    - change: modification in work/resources that has serious economical conseq
    - clarification: adding more details that support
    - exceptions
        - competitive product launched, emergency work, unavoidbale loss of business support
- abnormal sprint termination
    - exception scenarios, keep next sprint within same time-box, remove/reintroduce into bcaklog, record termination

- Sprint review
    - acceptance with shippable product
    - record feedback and update backlog

- SPrint retrospective
    - discuss issues 
    - realign backlog

### Buzzwords
- Velocity 
    - eval & improve use of scrum
    - amt work in each sprint 
        - PBIs / tasks / importance 
    - Ex: 200 PBI before release, 10 sprints -> 20 PBI / sprint
        - usually do a range

- Technical Debt
    - incr cost of working on app with "not quite right" code
        - poor design / unnoticed defects / unsuff test coverage / poor integration & release / lack exp,tool,tech
    - Unavoidable technical debt
        - risk-based, third-party software integrated
    - Strategic tech debt
        - tools to monitor and pay back tech debt 
    - Managing accrual of tech debt
        - good practices, well-define goal, monitor debt & consequences
    - make debt visible to business & tech people 
    - payback debt incrementally
        - end of each sprint / n sprint / threshold 
- Task board
    - PBI | Task to do | tasks in progress | tasks complete

- Sprint burndown chart  (downward slope)
    - X axis: days within a sprint
    - Y axis: est effort-hours remaining 
-  Sprint burnup chart (upward slope)
    - X axis: days within sprint
    - Y axis: story points







    

