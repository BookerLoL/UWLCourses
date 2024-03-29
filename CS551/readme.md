# Notes

## Introduction

- HCI, human computer interaction
  - designing usable, reliable, efficient front-end
  - **design, implementation, evaluation, evolution** of user interfaces
- Good UI
  - attractive (first impression => best)
  - terminology familiar with user
  - seperated from application
  - meaningful error msgs
  - easy to use and understand
  - consistent and unambigious
  - provide as much help (tutorials/help systems/manuals)
- UI Software
  - event driven
- design models support consttruction and help validate UI
  - GUI <=> Event handler <=> Shared info
  - Interface Obj <=> Event handler <=> Control Obj <=> Model obj

## GUI Controls

- Text field
  - user input on **single line**
  - passwords should be hidden
- Text Area (display area)
  - displaying text
  - scroll bar
- Label
  - display name/text/message (purpose of another GUI Control)
  - banner/logo
  - display message (cleared afterwards)
- Buttons
  - **associated with button**
    - invoke simple action
      - complex -> sequence and coherent
  - uniquely named
- Toggle Button
  - strictly alternate between two dinstinct actions
    - on / off, lock / unlock, change colors of icons
- Radio button
  - select only 1 value from group
- Check box
  - selecting 1+ from group
- Drop-down box / Combo box
  - select 1 entry from list
  - saves space
- List box
  - select 1+ entry from list
  - usually comes with slider
- Menu
  - list of actions to invoke when selected
    - navigate / execute / display / parameters
  - header, and sub-menus
  - display all relevant stuff that relate
  - levels and items usually indirectly proportional
    - align items to task at hand
      - normal alignment: top -> bottom, left justified
      - horitonzal alignment
  - separate choices
    - sequence of occurrence, freq of occur, importance, semantic similarity, alphanumerical
    - space/lines betweeen groups of choices
    - menu titles catchy & appropriate
  - Types
    - Pull down
    - Context menu (based on current state/context)
      - usually right click
    - Hierarchical menu (0+ sub-menus)
  - Use if: ideal, helpful, and enhancement

## UI Design Principles

- Accessibility
  - support disability too
- Compatiability
  - user: dev consider diff groups
  - product: GUI merge with app domain
  - task: approp tasks for selected users
- Aesthetically Pleasing
  - first impression = best
  - meaningful contrast
  - align elements
- Configurability
  - easy to personalize/configure
  - sense of control
- Flexibility
  - easy to integrate with various apps
  - flexible for diff users
- Forgiveness
  - report appropriate error messages
  - provide appropriate help and motivate user to continue
  - prevent user from making mistake
- Consistency
  - adv
    - user gets correct mental model
    - easy to learn and use
    - similarity across products
  - ex:
    - exit: close window and save app
    - quit: close window and don't save
    - x: exit
    - Ctrl C: copy
  - messages for prompts, feedback, and error messages
    - accpet / decline for license aggreemnents
    - ok
    - Pointer error: Null pointer exception on line 1098
  - colors
    - consistent throughout interface
  - layout structures and elements
    - exceptions are acceptable
    - same size, location, etc
  - fonts and pictures
    - same font and size, use consistent format
  - user operations
    - same operations and tasks
  - shortcuts
    - key strokes, abbreviations, macro facilities
  - Informative feedback
    - confirm user action as much as possible
      - prompt / pop-up windows
        - helps to prevent mistakes
    - no need to display message if user closes window
      - unless they don't save ?
    - descriptive and friendly error messages
- Error Prevent
  - protect user from introducing errors
  - verify correct seq actions (force back button)
  - provide "undo"

## Direct Manipulation System

- representation of objs, reversible/incremental/rapid action and feedback
- Goals
  - feeling of interface mastery
  - high deg competence in performing tasks
  - easy to learn and move to advance features
- Challenges
  - too much space (maybe)
  - need to know icons and grpahical elements
  - standardization of visual representation (maybe)
- Icon Choosing
  - represents action/obj
  - limit amt
  - visible and standout from background
  - additional info: tool tip, etc
- Metaphors
  - symbol/icon = realworld entity
  - Folder with folder icon
- Advantages
  - beginners learn quickly
    - tutorial optional
    - next level quickly
  - users confidence using it
- Examples
  - Home alarm, Tele-medicine, flight simulators

## User Profiles

- Characterization of user/set of users
  - Differences in background knowledge (domain vs UI concepts)
    - level of training, frequency of usage, ability to interpret error messages
- Difficulties
  - Not predicting user expctations
  - Hard to assess what knowledge a particular user has

# Starting Point

- Novice and First-time users
  - Novice
    - May have little domain and UI knowledge
  - First-time
    - may have sufficient domain knowledge but little knowledge of UI
  - Preferable to have
    - dialog boxes
    - small set of easy to remember commands/instructions
    - smal simple set of actions
    - step-by-step tutorial
    - manual
    - on-the-fly example
- Knowledgeable Intermittent Users
  - sufficient domain and UI knowledge
  - Consistency is most important
  - protection from danger when user explore additional features
    - warnings
  - online help system
  - wlel structure menus/instructions
- Expert and Frequent Users
  - experts in domain and UI
  - short, direct, unambiguous messages
  - short cut keys rather than menus
  - tailor user interfaces
- Challenges
  - users gain knowledge in application domain, but not UI concepts, vice versa
- User Centered Design (UCD)
  - user profiles considered
  - provide info about potential users and expectations
  - complexity may suggest various user profiles
- Task profiles
  - identify tasks by each group
  - rank them for each category
  - combine tasks into common groups
  - include shadowing/multiple screen effects to distinguish groups

## Animation

- illusion of motion
- enhance visual feedback
  - grabs attention & explains
- transitions, progress, enhance help system (guide)
- might not be needed, can other ways, irritate expert users, bored
  - provide option to turn off
- use timer at pre-defined intervals (realistic)
- avoid discrete jumps
  - motion blue: shows trace of object moving

## Cosmetics

- emphasize logic/info, differences, attractive
  - maybe unacceptable
- Consider culture, subjective, application, etc
- Problems
  - choosing color, overuse of color, rending color depends on device
- Guidelines
  - monocrome first, colors add conservatively (necessary)
    - Discrimination: no more than 4, widely spaced on spectrum
      - red, yellow, green, blue, brown
    - Comparative discrimination: no more than 6 or 7 colors
      - orange, yellowish green, cyan, violet, magenta
  - Bright colors for extended reading or older viewers
  - necessary actions
    - warm colors
  - Status / background info
    - green, blue, violet, purple
  - Emphasize
    - bright colors / highlighted colros
  - emphasize separation
    - contrasting colors
  - Choose colors for majority of users
  - Contrast between background and foreground
  - Same color for similar actions
  - Provide facility to change colors
- Fonts
  - Use propritionze to size of UI element
  - readability
  - Styles used only when necessary / emphasize
  - colors, contrast with background
- User choice and usability testing
  - preferable to not let users change font
- Metaphors
  - symbol/icon represent real world entity
    - check with users

## UI for Temporal and Real-Time Systems

- Real-time
  - based on real-world clock
  - controller program used by airport control tower
- Temporal
  - based on other events in system
  - robot picks up objects from moving conveyor belt at fixed intervals
- Safety-Critical
  - input and output can not have failures
- UI
  - display values on time, accept values on time
  - color codes highly recommended
  - consistency is mandatory
  - language must support threads
  - display clock all the time
- State Transition Model
  - helps with testing
    - track system execution
  - try to only consider stable states, not transient (unstable)
  - alert states
    - capture user's eyes right away
  - control object
    - separate application objs from event handlers
- resolve non-deterministic issues

## GUI Testing

- Test every GUI element
- test GUI navigation/walkthrough
- test observable functionality
  - use appropriate inputs and validate
- state transition diagrams
  - helps with hidden/shadowed controls
- all visible functionalities invokable
- consistency check
- usability testing
  - easy-to-learn and use?
    - experts advice, suervy users, compare
  - may need on-line help system?

## User Interface Evaluation

- Perfect UI is almost impossible
  - need saturation point, iterative process
- Evaluation techniques
  - Expert Reviews
    - heuristic evaluation
      - expert must be familar with domain and UI concepts
    - Guidlines Review
    - Consistency checking
    - UI walkthrough
    - Formal evaluation
    - Problems
      - agreeing with comments is difficult
      - hard to compare with other testing methods
  - Usability Testing
    - report
      - tasks complete, recommendations, and changes
    - generate test cases
    - may use external users
    - Discount usability engineering
      - user interview -> prototype -> testing
    - field tests
      - use it in real application for period of time
        - collect data and fix errors
        - not suited for safety-critical apps
    - Competitive usabiliy testing
      - compare UI with similar products
  - Surveys
    - offline, prepare questionnaire
    - inexpensive but time consuming
    - collect info, ensure participants are good
  - Acceptance Tests
    - QA group
    - definite criteria for acceptance
      - measurable criteria
        - time for learning
        - UI response time
        - number of errors over X time
        - productivity improvement
        - etc

## Other Applications

- Web Applications
  - Components
    - Brower, Server, DB server
- Mobile Application

  - Components
    - presentation, business, data layer
    - cross-cutting (security, config, etc)
    - local data & cache
    - data sources / services
  - Simple GUI, people ignore complex ones

- UX vs UI
  - UX: usability, end users, before UI design
  - UI: look and feel, screens, layouts, and etc

## Use Case Model for UI

- actor, specialization, association, use case, depdency
- simple and easy to use
- semantically weak
