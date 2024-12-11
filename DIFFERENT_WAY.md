# StarshipOS - A Different Way of Thinking.

Our vision is to create a state-of-the-art Operating System where every component, from files to memory management,
adheres to object-oriented principles. The OS will support running Java applications directly and provide an
environment where `jshell` can be used interactively as early as possible during the boot process. Ultimately,
the goal is to develop an object-oriented filesystem that utilizes virtual memory for enhanced performance
and persistence.

## No files.
What, No files? Are you insane? Yes, no files. No, I'm not insane.

If one has ***LOTS*** of memory it doesn't matter how big the heap of objects becomes.
In addition there's 'built-in' security potential if your Object object has ACLs
then every other instantiated object will inherit the ACL trait by transitivity. This 
would also apply to any other attributes introduced into Object object.

Since we have ***LOTS*** of 'memory' and no files we're only limited by the size
of the attached mass storage device. Think about the possibility of including cloud 
storage as 'RAM' along with your RAM and attached storage devices. Just a thought
that brings us to...
## HDD/SSD used as virtual memory.

```
+------------------------------------------+
|                High Memory               |
| $FFFF FFFF FFFF FFFF FFF FFFF FFFF FFFF  |
|   Virtual memory as persistent storage   |     
+------------------------------------------+
|              Storage metadata            |
+------------------------------------------+
|                                          |
|                                          |
|             Java Heap & Stack            |
|                                          |
|                                          |
+------------------------------------------+
|                  Low Memory              |
| $0000 0000 0000 0000 0000 0000 0000 0000 |
|             Working physical RAM         | 
+------------------------------------------+
```
Here's how it works. The smallest partition possible is a mounted as the `boot` 
partition as an ext4 partition to get us into Java world as quickly as possible. 
In our `Java World` a partition will be dedicated to ***virtual memory*** as a 
block device and ALL that space will utilized as memory for the JVM. Our GC will 
not only scavenge unused Objects but it will also reorder Objects so those with a
`sticky` flag set will be placed in the highest address which will persist them on
the SSD/HDD by virtue of their `sticky`flag. In addition there will be a `dirty`
flag that indicates if an object in persistent storage needs to be rewritten. 
These persistent objects "live" in high memory and are actively maintained. This 
is a constant growing and shrinking area of the block device. At the lower 
boundary of the persisted objects area there will be a dynamic metadata area for 
the metadata belonging yo persistent objects. That too is a dynamic data structure. 
Naturally, there will be a `fence` between our `working` RAM/SDD/HDD. Currently, 
the zgc garbage collector is the one I've selected as our starting point and is
compiled into our JDK. As to which garbage collector to start with is open to
discussion. We may even write one from scratch.

Here's an interesting thought. Integrate some machine learning into the gc and mm
so as the they *learn* to optimize themselves over time.

### So now I have a bunch of objects. Now what?
Use ***your*** imagination. My imagination envisions a *runner* object that can 
wrap any legacy Java or Linux (ELF?) and allow them to function by `installing` 
them into our wrapper. Now we have a **new** way of thinking about how we want 
to model and shape **our world**. You have no limit on dimensions or time. You
are figuratively in a sea of foamy objects that you are free to use as you wish.

Think about this, your world will come stocked with an initial set of objects that
are predefined and give you some basic tools to work with. A maven repository of
all available objects will host things like maybe `Wine`, `Libre Office`, `Jetbrains
IntelliJ IDEA` and so on. Porting existing Linux programs to StarshipOS becomes a snap,
no pun on Debian snaps intended. You are free to organize your objects any way you
see fit.

This brings us to the UX topic. There will be a command line available as a highly
customized `jshell`. There will be a JavaFX Graphic User Interface eventually
***AND*** picture a VR UI as the gateway to your virtual world.

Here's how I *see* **my world**, I'm seeing $\lambda$-CMB and The Standard Model 
with quarks, leptons & hadrons as my starting point if I had a supercomputer and
unlimited storage. I start out in my `workshop` with all my tools stowed away 
like a good six $\sigma$ kinda guy. I go to the store (repository) and *buy* 
(figurative) some materials, supplies & tools to build something I have in mind. 
Through my VR interface I `build` what it is I wanted to build in my virtual 
workshop. We are allowed to transit from the micro to the macro and back 
seamlessly.

That's *my* world, Your's may be quite different. This *is* a new way of interacting
with your computer so use *your* imagination to shape **your** own world anyway
upi like. If you're a disorganized slob or an anal retentive engineer it doesn't
matter, do things **your** way.



