# Book Reading Tracker
## CPSC 210 Term Project
### *By Shawn Xihao Wu*


This application is intended to provide anyone who loves to read a good way 
to keep track of their readings.

As a university student, I often find myself lost in 
the mountains of readings assigned by each course. I want to build a simple program 
that could help me stay on top of my readings, and I hope that it could 
help others who feel the same.


### User Stories:

- As a user, I want to be able to add books to my bookshelf
- As a user, I want to classify books by genre when adding books to the bookshelf


- As a user, I want to be able to see how many distinct genres are there on the bookshelf and what are they
- As a user, I want to be able to view the list of books on my bookshelf and their genres
- As a user, I want to be able to see how many books are in one certain book genre (1 book can have multiple genres)


- As a user, I want to be able to select a book and update how many pages I have read for this book


- As a user, I want to be able to see all my reading progress for each book in terms of percentage rounded to the nearest tenth decimal
- As a user, I want to be able to see my total reading progress in terms of percentage rounded to the nearest tenth decimal

- As a user, I want to be able to save current collection of books to file
- As a user, I want to be able to load previous collection of books from file and resume exactly where I left off last time

### Phase 4: Task 2

```
Thu Mar 31 19:53:50 PDT 2022
Added <Intro to Topology> to bookshelf!

Thu Mar 31 19:54:03 PDT 2022
Progress of <Intro to Topology> on bookshelf updated: 
Now on page 215 of 516! 41.7% read!

Thu Mar 31 19:54:21 PDT 2022
Added <Crime and Punishment> to bookshelf!

Thu Mar 31 19:54:42 PDT 2022
Added <War and Peace> to bookshelf!

Thu Mar 31 19:54:56 PDT 2022
Progress of <War and Peace> on bookshelf updated:
Now on page 35 of 381! 9.2% read!

Thu Mar 31 19:55:05 PDT 2022
Progress of <Crime and Punishment> on bookshelf updated: 
Now on page 151 of 161! 93.8% read!
```
### Phase 4: Task 3

Looking closely, one can probably notice that my user stories revolve around 
two groups of functionalities: 1) something to do with book genres, and 2) something to do with reading progress.
However, there are only two relevant classes in my `model` package, `Book` and `Bookshelf`, that would support these functionalities.
This means that I have crammed those two groups of functionalities into those two classes, resulting in a lack of cohesion.

My `Book` class keeps track of its genres, calculate and maintain its reading progress. 
It could be argued that those two responsibilities have little to do with book itself. 
More seriously, my `Bookshelf` class not only just stores a list of books, which is what a bookshelf is for, 
but also maintains a list of distinct genre names, gives out a list of book of certain genre, and calculates the total 
reading progress of all the books on bookshelf. 
That's too many responsibilities for one class.

To maintain the latter two functionalities, I have to loop through a list of `Book` objects in `Bookshelf` frequently.
This is rather inefficient, and not helpful if, in the future, I want to add new functionalities regarding book
genres or reading progress.
It is also not helpful if I want to debug my code just on those two sets of functionalities.

#### To address the issues above, I propose 3 design improvements:

#### 1) New classes for genre related functionalities
To delegate the responsibility regarding book genres, I think we can create two new classes 
`Genre` and `GenreManager`:

```java
public class Genre {
    private String name;
    private Set<Book>;
    // ...
}

public class GenreManager {
    private Set<Genre>;
    // ...
}
```
    
There now should a bidirectional relationship between `Book` and `Genre`:

```java
public class Book {
    private Set<Genre>;
    // ...
}
```
    
Whenever a user adds a new genre tag to a `Book` object, the program would create a `Genre` object and add it to
`Book` and `GenreManager`. At the same time, the `Book` object will also be added to `Genre`. So that we can more easily
keep track of how many genres there are and what are they, and how many books there are of certain genre and what are they.

#### 2) New classes for progress related functionalities
Similarly, to delegate the responsibility of keeping track of the reading progress of each book, I think we can create 
two new classes as well: `Progress` and `ProgressDashboard`. Each `Book` stores a `Progress` object so that we can move 
the math of calculating the progress of each book into `Progress`. And `ProgressDashboard` should store the total reading
progress, and the individual progress of all the books.

#### 3) Limits on with whom classes in `ui` can establish association relationships
With many more new classes introduced, to reduce potentially unnecessary couplings, 
I think that there should not be any associations between classes in the `ui` 
package and `Book`, `Genre`, `Progress`, because they should be able to access `Book`, `Genre`, and `Progress` through 
`Bookshelf`, `GenreManager`, and `ProgressDashboard`.

This means that in my current phase 4 design, I should get rid of the associations between `WindowAddBooks` and `Book`, 
`WindowViewBooks` and `Book`, `WindowUpdateProgress` and `Book`, and `ReadingTrackerApp` and `Book`. I only included 
`Book` in their fields because I want a global `Book` variable. I can easily make it local. 
Also, it makes little sense that those classes need `Book` because they can easily get it through `Bookshelf`. 