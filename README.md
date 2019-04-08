# MindvalleyAssignment 
This is the practical test for android developer

* For this assignment I have used MVVM architecutre along with Android Architecture components like LiveData and ViewModel.


# AsyncLoader-Library
This library will help you to load images and json file asynchronously along with the data cache feature.

## How to use library
This library will provide three functions to load data

1. Display image to imageview asynchronously
```java

 AsyncLoader.get()
                .displayImage(url,
                 myImageView,
                 new ImageRequestListener() {
                     @Override
                          public void onLoadFailed(String errorMessage) {
                                
                            }

                            @Override
                            public void onResourceReady(Bitmap bitmap) {
                                
                            }
                        });

```
2. Image Downloading asychronously
```java

 AsyncLoader.get()
                .loadImage(url,
                            new ImageRequestListener() {
                            @Override
                            public void onLoadFailed(String errorMessage) {
                                
                            }

                            @Override
                            public void onResourceReady(Bitmap bitmap) {
                                
                            }
                        });

```

3. Json Downloading asychronously
```java

  AsyncLoader.get()
                .loadJson(url,
                new JsonRequestListener() {
                    @Override
                    public void onLoadFailed(String errorMessage) {
                    }

                    @Override
                    public void onJsonReady(String json) {
                });
```


