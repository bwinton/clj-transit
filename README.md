[![Build Status](https://travis-ci.org/st3fan/clj-transit.png)](https://travis-ci.org/st3fan/clj-transit)

This app provides an HTML5 dashboard that displays the arrival times of TTC Streetcars near the Mozilla Toronto Office.

To run this app in development mode you will need to have [Leiningen 2.0](https://github.com/technomancy/leiningen) installed. Then you can just do:

```
$ cd clj-transit
$ lein ring server-headless
```

The dashboard will run on [http://localhost:3000/index.html](http://localhost:3000/index.html)
