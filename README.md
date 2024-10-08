# Droid News

### Description
NewsApp is a fully funcional Android app that allows users to peruse news articles from a plethora of sources, save their favorite articles, and open the articles on a mobile webview to read them.

### Specifications
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
  <img src="https://github.com/Abid-Hussain36/NewsApp/assets/96400204/c8bad715-4228-44f6-81ba-058df0a3704c" width="150" />
  <img src="https://github.com/Abid-Hussain36/NewsApp/assets/96400204/a2d32a9c-06d5-40d0-9c87-4d2d21779eed" width="150" /> 
  <img src="https://github.com/Abid-Hussain36/NewsApp/assets/96400204/da912e54-25f9-4f29-9f4f-c1d1bccfadc9" width="150" />
  <img src="https://github.com/Abid-Hussain36/NewsApp/assets/96400204/fcea795c-f7d0-492a-adf1-b64a635437b7" width="150" />
  <img src="https://github.com/Abid-Hussain36/NewsApp/assets/96400204/f0de558b-3c95-47c3-85a5-eb3db3f16333" width="150" /> 
