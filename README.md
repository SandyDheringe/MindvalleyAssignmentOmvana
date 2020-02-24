# MindvalleyAssignmentOmvana

# Task
Imagine you are on the Pinterest Android team and you are working with some colleagues on the pinboard (the scrolling wall of images), you split up the tasks among each other and your task is to create an image loading library that will be used to asynchronously download the images for the pins on the pinboard when they are needed.


# Requirement
  1. Coding in "Kotlin" is a must.
  2. Use the following url for loading data: https://pastebin.com/raw/wgkJgazE
  3. Images and JSON should be cached efficiently (in-memory only, no need for caching to disk);
  4. The cache should have a configurable max capacity and should evict images not recently used;
  5. An image load may be cancelled;
  6. The same image may be requested by multiple sources simultaneously (even before it has loaded), and if one of the sources cancels the load, it should not affect the remaining requests;
  7. Multiple distinct resources may be requested in parallel;
  8. You can work under the assumption that the same URL will always return the same resource;
  9. The library should be easy to integrate into new Android project /apps;
  10. You are supposed to build a solid structure and use the needed programming design patterns;
  11. Think that the list of item returned by the API can reach 100 items or even more. At a time, you should only load 10 items, and load more from the API when the user reach the end of the list;
  12. Usage of Material Design UI elements (Ripple, Fab button, Animations) is an advantage;
  13. Writing test cases is a must.
  
# Architecture
  MVVM

# Library Used
  1. gson
  2. loopj
  3. Custom Image load library
