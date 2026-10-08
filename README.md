# JomonJ

A port of my CLI app [jomon](https://github.com/tagaroggu/jomon), which was originally written in c.

Compile: `javac -d bin src/**/*.java`

Run: `java -cp bin junipyr.jomonj.JomonJ`

Helpful info can be printed with `java -cp bin junipyr.jomonj.JomonJ -h`

## TODOS:

- [ ] Feature parity with original project
    - [ ] Verbosity
    - [x] Random sort

- [ ] Pull in args from `Args.getArgs()` where needed instead passing args object around