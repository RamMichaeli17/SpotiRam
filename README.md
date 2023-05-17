<div id="top"></div>

<!-- PROJECT SHIELDS -->
[![GitHub repo size][reposize-shield]](#)
[![GitHub language count][languagescount-shield]](#)
[![Contributors][contributors-shield]][contributors-url]
[![Stargazers][stars-shield]][stars-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]
[![Gmail][gmail-shield]][gmail-url]






<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/RamMichaeli17/SpotiRam">
    <img src="https://user-images.githubusercontent.com/62435713/191277564-b5f220dc-f9de-4e5a-963f-e6ffe905dc3e.png" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">Developing Client-Side in Android Environment 2 Project: "SpotiRam"</h3>

  <p align="center">
    Experience seamless music playback on the Android music player app as it flaunts a user-friendly interface, featuring a dynamic and consistent song list, allowing you to enjoy your favorite tunes in the background, even while navigating other applications with ease.
    <br />
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#download">Download</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contributors">Contributors</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>


## About The Project
<img src="https://user-images.githubusercontent.com/62435713/191279715-1884391b-c601-41f0-ab0e-fa86b4af60db.png" width="300"/>


In this project you may find several subjects such as: <br>
1. Data storage using Shared Preferences: Saving and retrieving basic data using Shared Preferences, providing a simple and efficient way to store key-value pairs persistently.

2. Internal storage for data storage: Utilizing the application's internal storage to save and read data, offering a private and secure storage space for app-specific files.

3. Saving various data types: Handling the storage of different types of data, including text, images, and objects implementing the Serializable interface, enabling comprehensive data management within the application.

4. Exception handling and object serialization: Implementing proper exception handling during object serialization and deserialization using transient modifiers and personal handling techniques like writeObject and readObject, ensuring robust and error-free data processing.

5. RecyclerView for advanced visual control: Utilizing the RecyclerView component, which acts as a versatile replacement for AdapterViews (such as ListView), managing automatic recycling, leveraging ViewHolders for improved efficiency and performance, and providing flexibility in displaying and managing data.

6. CardView for stylish information display: Incorporating CardView, a UI component that presents information in visually appealing cards with rounded corners, offering an elegant and modern design for displaying content.

7. Service for long-term tasks: Leveraging the Service component to execute long-running tasks without a user interface, allowing background processing and efficient resource management.

8. IntentService for sequential task execution: Utilizing IntentService, a subclass of Service, to execute tasks sequentially in the background, ensuring proper task order and efficient task management.

9. Foreground Service for extended lifespan: Enhancing a Service by transitioning it to a Foreground Service through a notification, increasing its lifespan and preventing termination by the system in low memory situations, providing a reliable and uninterrupted operation.

10. MediaPlayer for music playback: Utilizing the MediaPlayer class to create a music player, leveraging its functionality in combination with a Service to provide seamless music playback within the application.


<p align="right">(<a href="#top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

Follow these simple steps:

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/RamMichaeli17/SpotiRam.git
   ```
2. Run the program
   ```sh
   press shift+F10 or press the "Run app" button
   ```
   ![image](https://user-images.githubusercontent.com/62435713/190141964-e8bebdf4-7b16-470c-acd1-ce3e369f9ca3.png)


<p align="right">(<a href="#top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

The application is a music player that offers a seamless and enjoyable music listening experience on Android devices. It allows users to create and manage a personalized list of songs, complete with album art and song links. The user interface presents the songs in a dynamic and consistent manner using RecyclerView and CardViews, providing an intuitive and visually pleasing display.

Users can edit the list by rearranging the songs through drag-and-drop functionality. Clicking on a song in the list opens a detailed view that showcases the song with a high-quality, full-size image and additional information. Swiping on a song triggers a confirmation dialog for deletion.

The application also allows users to expand their song collection by adding new songs. They can capture an image using the device's camera or choose an image from the gallery. Additionally, users are responsible for providing a proper song link associated with each added song.

To facilitate music playback, the application incorporates a player button that plays the songs in the list sequentially. The music player is implemented as a Foreground Service, ensuring uninterrupted playback even when the user interface is closed. The Foreground Service also generates a notification that provides full control over playback, allowing users to pause, stop, and skip songs.

Overall, the application offers a comprehensive and user-friendly music player experience, empowering users to manage their song collection, customize the list, and enjoy uninterrupted music playback in the background, even when using other applications on their device.

<details open>
<summary>Screenshots</summary>
<table style="width:100%">
  <tr>
    <td><img src="https://user-images.githubusercontent.com/62435713/191279715-1884391b-c601-41f0-ab0e-fa86b4af60db.png"/></td>
    <td><img src="https://user-images.githubusercontent.com/62435713/191279733-20690394-d8bd-4e14-86c2-174863a089e0.png"/></td>
    <td><img src="https://user-images.githubusercontent.com/62435713/191279749-48143520-a91e-4a70-bebc-7a84cb618b88.png"/></td>
  </tr>
  <tr>
    <td><img src="https://user-images.githubusercontent.com/62435713/191279761-8c489471-77a8-400a-9b52-bb13abb4c6a7.png"/></td>
    <td><img src="https://user-images.githubusercontent.com/62435713/191279778-f9ecfce5-7982-4939-9f7d-622ec4815423.png"/></td>
    <td><img src="https://user-images.githubusercontent.com/62435713/191279836-919adb6b-33e3-4b5a-9510-af45c6c60969.png"/></td>
  </tr>
  <tr>
    <td><img src="https://user-images.githubusercontent.com/62435713/191279917-c45b34d1-c5a7-4595-8c63-7be8cee91d4d.png"/></td>
    <td><img src="https://user-images.githubusercontent.com/62435713/191279943-56a8e469-eabb-4b24-a5c6-33873f4a47b5.png"/></td>
    <td><img src="https://user-images.githubusercontent.com/62435713/191279969-8c0a8644-f0a8-48c2-b6aa-cf16a8ff89e5.png"/></td>
  </tr>
  <tr>
    <td><img src="https://user-images.githubusercontent.com/62435713/191280012-21172d95-f271-4ddd-88c1-85b77085b398.png"/></td>
    <td><img src="https://user-images.githubusercontent.com/62435713/191280062-75b0aaa4-9192-4c31-bde7-79ec6e9b2024.png"/></td>
  </tr>
</table>
</details>


### Youtube:
[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/hG-5Kn1kotk/0.jpg)](https://youtu.be/hG-5Kn1kotk)

<p align="right">(<a href="#top">back to top</a>)</p>

## Download
  <a href="https://github.com/RamMichaeli17/SpotiRam/raw/master/app/release/SpotiRam.apk">
    <img src="https://user-images.githubusercontent.com/62435713/190144702-6abd364e-ec2d-4705-a486-b2de7b32ec7f.png" alt="Download" width="400" height="130">
  </a>

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- LICENSE -->
## License

Distributed under the MIT License. See [`LICENSE.txt`](https://github.com/RamMichaeli17/SpotiRam/blob/master/LICENSE.txt) for more information.

<p align="right">(<a href="#top">back to top</a>)</p>



## Contributors

We thank the following people who contributed to this project:


<table>
  <tr>
    <td align="center">
      <a href="https://github.com/RamMichaeli17">
        <img src="https://avatars.githubusercontent.com/u/62435713?v=4" width="100px;"/><br>
        <sub>
          <b>Ram Michaeli</b>
        </sub>
      </a>
    </td>
  </tr>
</table>

<p align="right">(<a href="#top">back to top</a>)</p>




<!-- CONTACT -->
## Contact

Ram Michaeli - ram153486@gmail.com

Project Link: [https://github.com/RamMichaeli17/SpotiRam](https://github.com/RamMichaeli17/SpotiRam)

<a href="mailto:ram153486@gmail.com"><img src="https://img.shields.io/twitter/url?label=Gmail%3A%20ram153486%40gmail.com&logo=gmail&style=social&url=https%3A%2F%2Fmailto%3Aram153486%40gmail.com"/></a>
<a href="https://linkedin.com/in/ram-michaeli"><img src="https://img.shields.io/twitter/url?label=ram%20Michaeli&logo=linkedin&style=social&url=https%3A%2F%2Fmailto%3Aram153486%40gmail.com"/></a>
<p align="right">(<a href="#top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
[reposize-shield]: https://img.shields.io/github/repo-size/RamMichaeli17/SpotiRam?style=for-the-badge

[languagescount-shield]: https://img.shields.io/github/languages/count/RamMichaeli17/SpotiRam?style=for-the-badge

[contributors-shield]: https://img.shields.io/github/contributors/RamMichaeli17/SpotiRam.svg?style=for-the-badge

[contributors-url]: https://github.com/RamMichaeli17/SpotiRam/graphs/contributors

[stars-shield]: https://img.shields.io/github/stars/RamMichaeli17/SpotiRam.svg?style=for-the-badge

[stars-url]: https://github.com/RamMichaeli17/SpotiRam/stargazers

[license-shield]: https://img.shields.io/github/license/RamMichaeli17/SpotiRam.svg?style=for-the-badge

[license-url]: https://github.com/RamMichaeli17/SpotiRam/blob/master/LICENSE.txt

[linkedin-shield]: https://img.shields.io/badge/linkedin-%230077B5.svg?style=for-the-badge&logo=linkedin&logoColor=white

[linkedin-url]: https://linkedin.com/in/ram-michaeli

[gmail-shield]: https://img.shields.io/badge/ram153486@gmail.com-D14836?style=for-the-badge&logo=gmail&logoColor=white

[gmail-url]: mailto:ram153486@gmail.com

[product-screenshot]: images/screenshot.png
