# Gesture Screen Lock
Gesture Screen Loock is an Android screend lock with a gesture recognition algorithm impemented from scratch.
Original project is SLock(https://github.com/algoprog/SLock).

# The algorithm

The task of checking if two gestures match is equivalent to checking the similarity of two sets of points. Before comparing the two sets, we normalize them; we move the sets so that the first point of each set is (0,0) and then we resize each set to fit in a predefined frame. SLock uses a simple point sets similarity measure. Let A be the set of points of the saved gesture and B the set of points of the drawn gesture to unlock the device. To measure the similarity of the two sets we use this formula:

![alt tag](https://lh3.googleusercontent.com/-ZflNgwrO4DU/VlWXcOwKZWI/AAAAAAAAAY4/B6IIvsOFDOA/s413-Ic42/formula.png)

where:

![alt tag](https://lh3.googleusercontent.com/-qZxm8JjpqOM/VlWZa0SwniI/AAAAAAAAAZI/peWVOTDMifs/s187-Ic42/expl.png)

What we get from this formula is a measure of distance between the two sets in pixels. If this distance is above a predefined threshold then the gesture in accepted as correct.
