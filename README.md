# Android Jetpack Compose News App

### Description
NewsApp is a fully funcional Android app that allows users to peruse news articles from a plethora of sources, save their favorite articles, and open the articles on a mobile webview to read them.

### Specifications###
* App fetches data for latest headlines within 7 days of the current date from the NewsAPI on launch using the Ktor library.
* The recieved data is parsed and displayed using a LazyColumn composable holding a list of Card composables to display each of the fetched articles.
* The images for each of the fetched articles are asynchronously loaded with the Coil library.
* The user can enter a desired topic or keyword into a search bar to fetch articles from NewsAPI whose titles contain the entered text.
* The user can select the "Save" button in the Card composable displaying an article to save this article for future reading by making use of the Room library.
* The user can select the "Open" Button in the Card composable displaying an article to open this article in a mobile webview by making use of the AndroidView composable.

### What I Learned
* How to use Compose Navigation to facilitate navigation between screens as well as passing data between screens.
* How to make API requests with the Ktor library.
* How to use the produceState method to maintain a state for data that updates when another state changes.
* How to persist data to a local database with the Room Library.
* How to use the AndroidView composable to open a mobile webview with a URL.
* How to methodically debug software by paying close attention to detail.

### App Demo
