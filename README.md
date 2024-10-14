Few words about the decisions I've made here: 

Tech Stack: 
Jetpack Compose, MVVM

Visually it's just as simple as it gets. I did not put any effort into theming and styles. 
In real life I'd follow some design and the whole application would be themed and styled. 
For picture loading I'd consider whether to use 3rd party like Picasso.  
AsyncImage I used is a bit crude. 

Architecture: 
For brevity I didn't bother with the business layer. In a larger app I would also convert DAOs to business model
and maybe further to UI model if needed. Witch clean architecture it's also important not to overdo it though. 
I've seen apps where 80% of the code is just mapping between layers and it's just a nonsensical inflexible approach

Modularisation: 
In a full blown application we'd put things like network and repository layers into their own modules. 
Same with complex screens and functionalities. 
But obviously modules should be used as they're needed. They're not needed here.  

Testing: 
I started with testing, but due to time constraints I had to cut down on that. 
Obviously normally there'll be at least unit tests for all the testable classes, including all the 
ViewModels. For UI tests: Jetpack Compose, UI Automator if needed. In case there are still Views in the app we'd have to use Espresso as well 
For running faster instrumented tests I'd use Robolectric, since it can run on JVM in CI environment instead of device / emulator which is inherently less stable.

In the few unit tests I made I used Mockito. I'm not on any side in the ancient dispute between using mocks vs fakes, 
just more accustomed to mock approach, since all companies I worked for were using those.   

Branching: 
Normally I'd use Github flow and for each functionality there would be a branch and pull request 
Within the branch commits would be smaller so each smaller change can be traced, 
while merged pull request would be squashed so there's no mess in the git log.   
Here the commits I made are larger, each equivalent of a squash merge from a pull request.  
Additionally all release commits would be tagged


