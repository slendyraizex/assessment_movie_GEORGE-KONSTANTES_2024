This is my coursework on Mobile Computing the Mobile Movie Explorer App.

I want to go through the process of creating my project and also make clear what exactly I have made.
So I have mostly completed what was required from the coursework and for aesthetic reasons and because
I plan to keep working on this project in my own personal time I have also added a few extra features.
When opening the app you are greeted with the startActivity which will prompt you into the loginActivity.

There loginActivity is there purely for looks and is not properly implemented as it was not required for
the coursework. You log in with username and password credentials: username: test password: test
Register now would have redirected you to a registerActivity where you would create an account but I
didn't bother for now as I had tight time constraints and more important things to implement.

After you login you are greeted with the mainActivity. The search bar at the top is not functional, you
can input text but it will do nothing. Next we have the three recycler views with the movies sorted by
Best(highest vote average), Most Popular(highest popularity score) and Recent Releases (release date).
At the bottom i created a bottomAppBar that can redirect from the mainActivity (Explore) into the
favoritesActivity (Favorites) the third button is undecided what I will do with it and is there for
aesthetic and symmetry and the fourth button will be an activity where a user can see and edit their
personal data. both the TB (to be decided) and the profile button are there for symmetry reasons and
provide no functionality.

When you click on a movie from the recycler views you go into the detailActivity of the specific movie
you clicked on. There you have a back button that takes you back to the previous activity. A share button
to share the movie on a different app and a favorite button that saves the specific movie into the
favoritesActivity (more on it later). Next you can see the movie's poster, title, rating and runtime,
followed by the movie's genres, its overview, its release date, its actors and finally the crew members.

Clicking on Favorites in the bottomAppBar redirects you to the favoritesActivity. Important thing to
mention here: it is bugged and I was not able to fix it I will go into more detail now. So, you can add
the movies just fine and they will be added to the recycler view in the favoritesActivity but while they
are there and you can click on the movies and you will be redirected to their respective detailActivity,
the movies themselves are not visible. You should be able to see their posters and titles just like in
mainActivity, but you can't. They are there and you can interact with them, you just can't see them.

Finally for the network check its successfully implemented on the main, detail, favorites and login activity.



