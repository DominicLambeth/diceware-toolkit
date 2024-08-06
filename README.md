# Diceware Toolkit

### Context:
This is the first Java project that I have worked on where I came up with the concept for the program on my own! It's also relatively more complex than the average beginner programming projects that I have made. It doesn't feature classes, inheritance, or polymorphism yet (things that I am aiming to include in my upcoming projects), but it does contain several custom functions. It also provides some error checking, and a "home" and "exit" command which work anywhere inside of the program to go back to the home page or exit the program.

### Concept:
This is a command-line based program that provides the user with several tools to help facilitate generating Diceware passphrases. Diceware is a method for randomly generating strong, but memorable passphrases by rolling five dice, and then looking up each resulting five digit number in a word list to find the associated word. This process is repeated until a passphrase of a desired length is generated. My program uses the EFF's official Diceware word list. Diceware Toolkit provides three components/modules to the user:

1. Diceware Passphrase Generator
    - This module generates random Diceware passphrases for the user, after the user inputs how many words they would like in their passphrase. This module combines the functions of the other two modules to achieve this.

2. Diceware Random Number Generator
    - This module uses the Java Math class's "random()" method to simulate 5 die rolls. It provides the user with a random, five-digit number to be used for Diceware. The user can use these numbers with their own preferred word list, or they can even use them in the 3rd module of the program (although at that point, you may as well use the 1st module).
  
3. Diceware Word Lookup Tool
    - This module allows the user to use their own five-digit number to look up the associated word on the built-in Diceware word list (EFF's word list). If the user would rather trust the randomness of physical dice over the randomness of Java's "Math.random()" method, this will allow the user to generate five-digit numbers with their own physical dice if they would like. 
