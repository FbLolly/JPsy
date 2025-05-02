## Graphics docs

Images are animations are treated the same way in this program,
They are both loaded in the ImageList class who has as primary
component an hashmap that makes it possible to access the images
whenever you need without loading unused ones. For example,
if "ImageList.getImages("slot");" was never called,
the "slot" sprite/s would have never been loaded or rendered.

To add a new image or animation, create a new properly named folder in the images/
directory and add your images inside of the newly created folder.
To start adding Images to your folder name them by animation frame 1.png...n.png.
To make the folder recognizable to the game, add an exists.txt file to your folder,
you may leave it empty for standard speed (one animation cycle in 2 frames, reccomended for single images)
or write an integer to signify how long you want the animation to last

## Example
writing 2 inside will make the animation last a single frame,
writing 4 inside will make the animation last 0.5 frames
writing 8 will make the animation last 0.25 and so on...

## Access to image with code
To access the images you may create an ImageList object and use imagelist.getImages("foldername")
to gain access to a linkedList of all sprites in the folder in animation order.
To then gain access to the Desired Animation frame you may want to call Defiles.getCurrentAnimationImage(animationlist);
This will return an Image Object that can be drawn to the screen