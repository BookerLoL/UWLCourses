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


### Role of Project Manager
- responsible for outcome, coordinate activties, knowledge of all phases, communicate with every single team member
- build & maintain group, set reasonable goals, measure progress, eval performance, motivate members, prov adequate facilities and resources for project

#### Characteristics 
- polite, patient, friendly, honest, courteous
- understand and listen
- knowledge of all tech areas 
- knowledge of app domain
- comm skills
- critical thinker, analyzer, coordinator

### Conflict Management
- Inevitable in group prj
- due to
    - scarce resources 
    - pressure (priorities/deadline)
    - personality issues
- if mng properly -> diff of opinion -> incr productivity and better dec making
    - else decr productivity
- deal with conflicts immed
    - importance and intensity
    - time pressure 
    - relative power of people involved 
    - motivation to resolve (short & long-term)
- **conflict management techniques**
    - Widthdraw / Avoid conflicts
        - 1+ people in conflict due to **personality** -> give up 
            - both equal -> try to convince one to withdraw
            
             Two people argue on choosing Java or C# as the development language. Project manager finds both languages and their associated tools are equally capable in completing the project. So, the project manager convinces one of them to withdraw.

            Two subgroups argue on choosing two different locations as the next meeting venue. Both locations are easily accessible and have all the resources necessary for the meeting. So, the project manager convinces one of the groups to withdraw

        
    - Smooth / accommodate
        - whats is done best by bother parties, emphasize areas agreement before differences
        - smooth argu and maintain harmony & relationships

        Two parties argue about a particular error popping up in the code blaming each other. Project manager explains how both parties worked well in the past, with no such errors; explains how these types of errors can be avoided or traced back; explains how one helps the other when there is a problem in coding


        Two parties argue on scheduling issues and try to choose their own schedule. Project manager explains the time pressure on completing the project; explains how they both did well in the past in completing the tasks on time.

    - Compromise / Reconcile 
        - solution -> degree satisfaction to all parties involved -> temporary/partially resolve conflict
        - lose-lose, use other techniques first 

        Two parties argue that the overall productivity decreased because of the incompetency of the other party (not sufficiently skilled, lazy, did not care for the project, …). Project manager finds that both parties are not doing what they are supposed to do. So, project manager takes a firm decision by himself on work allocation, time schedule and completion of tasks, and enforces them on both parties

    - Force or direct or dictate
        - win-lose 
        - justify decision: time/budget 

        Two parties argue that the project’s priority by upper management was lowered (and so project does not get enough funding) because of mistakes made by the other party. Project manager carefully analyzed the situation and found that there are more mistakes made by one party, say A, than the other and hence forces A to correct the mistakes.

        Two parties argue on who will demonstrate the final product to the marketing team. The project manager evaluated the presentation skills by both parties and gives a chance by one who did well in presentations in the past.

    - Collaborate/Solve the problem
        - study carefully, identify all srcs for origin of conflict, multiple viewpoints and insights, req transparency, open dialogue, cooperative attitude
        - win-win

        Two parties argue on using two different sets of libraries to improve the GUI. Project manager studies the two support provided by both libraries, ease of training for those who are not familiar with those libraries, steps necessary to adapt them with the product and so on, and then chooses the best among the two. He/she then explains the reasons to both parties.





    

