# Bing

## Introduction

To preface, Bing is an online web search engine designed and currently maintained by Microsoft.

Bing is the distant second most popular search engine only behind Google.

The search engine took many iterations of development to become a robust and functional software application. 

Bing, like many other well known robust software, apply security principles to their software to be as robust and safe as possible. 

Before diving into the security principles, I would like to mention that I am not a Bing user however I have used their software before.

I would also like to state that there was very little information that could be found about known bugs/security issues pertaining to this software application.

Because Bing is a proprietary software, knowing the internals is extremely difficult to get my hands on to write a report about, so I can only describe what I can see based on using the application. Here I would like to make a bold assunmption that internally Microsoft follows the safety principles to handle internal matters with this software.


## Economy of Mechanism

Bing is an online web search application that provides users the functionality to find resources using Bing's search engine.

The design beind this application is simply to allow users to provide input data such as text or images. After entering the input data, there is a possibility that resources that are related to the input data will appear such as websites, images, ads, and related searches. The main design of the application is rather simple which is to query resources based on input data. However, to state that Bing's design is small is easily deniable. 

Being a top search engine means that they must have many functionalities that users may use or want but additional functionalities will increse complexity and size of a design. Bing has many features which rely on behind the scenes tasks such as validating input, validating resources, filtering resources, using cached results, using search preferences, and etc. Not to mention that information is retrieved from multiple DB's, ensure safe connections and transfering information properly, preventing overworking the server requests and etc are all needed to provide even a remotely well unhampered search engine. 

Bing's fundamental design is simple at it's core but the necessity to provide additional functionalities to even compete only increases the size and complexity of the overall design. 

## Fail-safe Defaults

Being an online web search application requires allowing users the permission to query for resources. As of currently, there are not really any features that would require someone with more permissions to use the main essential functionalities. 

Though, if you log into your Bing account, you are given more privledges such as customizing what content you may see more of, see content that relates to your interests, receive notifications, get rewards and promotional games, and etc. These additional permissions do not really cause any signicantly obvious issues as these permissions do not cause any motivation for potential threat. 
Other users are unable to see information of another user regardless if they are a signed-in user or not. 

Bing only provides the essential functionalities that a normal user would need and only add luxury customizations for those users that decide to sign into their Bing account.

## Complete Mediation

As mentioned before, all typical users have access to the query resources that they are allowed to see. There are no immediately obvious checks on the access to query resources but there are a few checks for users with a Bing account.

All users that try to access their Bing Account are required to enter their credentials before gaining access to their account. As a signed-in Bing user, they have the ability to check their private asses such as subscriptions, passwords, privacy settings, and etc but these all require another log in check and then they are redirected to another website. 

Interstingly enough, Bing provides 3 types of search which filters out adult content. Changing these settings requires an additional confirmation button click on whether or not they agree to these settings. 

Bing provides the necessary security precautions for people with an account as their software application is primarilly designed to provide the requested queried resources.


## Open Design

Search engines are highly competitive and allowing your competitors to see your algorithms on fetching resources would jeopardize the need for that software as competitors will simply use your ideas and improve them. 

This long winded introduction is to say that Bing is a properitary software and keeps it's design a secret. Although the design is a secret, possession of things are kept protected. Take for instance, the information that each user's account has, it's being protected by the login feature. Only the account has potential information that could be valuable but they are being protected. 

Bing is not open designed but possession of things are still protected. 

## Separation of Privilege

Bing does not really offer much when it comes to obvious separation of privelges. Despite being lackluster in this section, the closest example that could maybe fit would be the additional checks on logging in.

Logging in requires your standard login credentials but also a confirmation code that is sent to your email. Although you may know someones email and bing password, a user still needs to have information to their email to fetch the code. This is the closest example of the idea of "two keys is better than one". Additionally, Bing provides two-factor authentication option that users can opt to select as an option in their account settings. Two-factor authentication would be another form of separation of privilege since you would also need the agreement with your other means of authenticating. 

## Least Privilege

This will sound like a broken record at this point, but Bing provides all users the ability to query for resources relating to that query. Users can also filter the adult content. These are essentially the least amount of privilege that a user needs to use this software application.

Only the personalized information and queries can be used by a sign-in account user. Access to more personalized customizations is the additional privilege a signed-in account user has over a normal user. 

Bing minizes the amount of privileges to users by providing all the essential functionalities, 

## Least Common Mechanism

Since Bing intentionally allows all users to query resources with filtering capabilties, these are essentially the only mechanisms that are shared among users. 

Signed-in users have a common mechanims of customizing features based on their personal use and that is basically all the mechanisms that they can do.

Bing users are limited to very little mechanisms but they are plenitful in terms of functionality. 

## Psychological Acceptability

Bing's overall GUI structure is very simple. You are given the ability to put into a box and then request for the resources. Even the settings page for signed-in account users has only simple select GUI components that they are only able to change settings that wouldn't really risk any threat. 

Overall users are inclined to use the software properly because there is not much else to do besides perhaps customizing their experience with the software. Irrefutably, this application makes users follow apply protection mechanisms but not even allowing users to mess with the protection mechanisms.

## Closing Words

Bing is one of the top online web search software application for a reason. Not only does Bing provide quality query retrievals but also is able to stay quite robust and safe for users to use without worrying too much about security issues. 

## Resources
- https://www.bing.com/
    - All information/facts were derived from using the software application itself.