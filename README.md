# fleky
Fleky is a simple app that demonstrates use of Kotlin in MVI architecture. The app uses RxJava, databinding and Firebase.

## features
The app shows you a list of memories you saved on your phone. A memory consists of picture, title and description. User can create new memory using a camera and save it to the backend service. Saved memories can be shared with other people using a standard android share intent.

## architecture
Each feature consists of

1. **Service** -- Service provides the data model of the application
2. **Presenter** -- Presenter obtains data model from Services based on **ViewIntents** and provides the **View** with a renderable **ViewState**. Between View and Presenter there's a unidirectional data flow on immutable objects.
3. **ViewState** -- ViewState is immutable to avoid common rendering problems. It has properties easily accessible for data binding.
4. **View** -- View creates ViewIntents based on user input and renders a ViewState.

