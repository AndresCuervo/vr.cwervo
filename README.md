# VR.CWERVO



This is a little [Reagent](https://github.com/reagent-project/reagent) app
combined with [A-Frame](https://aframe.io/) :)

## Development Mode

### Run application:

```
lein clean
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

## Production Build

```
lein clean
lein cljsbuild once min
```

---

Just some personal notes:

compiled this with:
```
 lein new reagent-figwheel routes-test +routes
```

And, to my surprise, it seems to be working out of the box! <3

This is the command to compile a static build:

```
lein clean && lein figwheel dev

```

In addition, running:

```
lein clean && lein cljsbuild once min
```
And then you can just statically export `resources/public` :)

TODO:
---

- [ ] Write a script to compile and push to gh-pages branch for easy deploy :)
- [x] Add the [Sass](https://github.com/vladh/lein-sassy) plugin for replicating
front page styles of vr.cwervo.com
- [ ] Port the front page of vr.cwervo.com to this!
- [ ] Port A-Frame JS to CLJSJS
