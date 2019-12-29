# Last layer megaminx solver

Last layer megaminx solver is an application that allows you to search for algorithms for solving any last layer situation on a megaminx.

## Downloading and installing

The solver runs on any platform that supports Java and requires at least 512 MB of RAM.

![Last layer megaminx solver v1.0](https://github.com/jazzthief81/llminxsolver/raw/master/docs/images/llminxsolver_ui.png "Last layer megaminx solver v1.0")  
[Download last layer megaminx solver v1.0](https://github.com/jazzthief81/llminxsolver/releases/download/v1.0/llminxsolver-1.0.jar)

The download is an executable JAR file and can be launched by double-clicking on it, assuming that a Java Runtime Environment is installed on the system.

## How to use the solver

### Setting up the starting position

Setting up the case that you want to solve can be done by dragging and dropping the pieces on the diagram that you see in the top-left pane.

Swapping two corners can be done by clicking one corner and dragging it to the other corner that you want to swap it with. An arrow indicates which pieces are being swapped while you’re dragging. Similarly two edges can be swapped by clicking one edge and dragging it to the other edge that you want to swap it with.

![Swapping corners](https://github.com/jazzthief81/llminxsolver/raw/master/docs/images/swap_corners.png "Swapping corners")
![Swapping edges](https://github.com/jazzthief81/llminxsolver/raw/master/docs/images/swap_edges.png "Swapping edges")
![Twisting corners](https://github.com/jazzthief81/llminxsolver/raw/master/docs/images/twist_corners.png "Twisting corners")
![Flipping edges](https://github.com/jazzthief81/llminxsolver/raw/master/docs/images/flip_edges.png "Flipping edges")

Changing the orientation of a piece can also be done with drag and drop gestures. Simply click on a sticker of a piece - corner or edge - and drag it to another sticker of the same piece that you want to move it to. The stickers of the piece will be cycled in the direction of the arrow that appears while you’re dragging.

Reverting the starting position back to the solved state can be done by clicking the **Reset** button.

### Selecting the allowed faces

The search is optimized for finding solutions

- using only **R, U** moves,
- using only **R, U, F** moves,
- using only **R, U, L** moves,
- using only **R, U, L, F** moves or
- using only **R, U, L, F, B** moves.

You have to select one of those 5 modes from the **Allowed faces** panel.

![Allowed faces](https://github.com/jazzthief81/llminxsolver/raw/master/docs/images/allowed_faces.png "Allowed faces")

For each of the 5 options, the solver will decide on a heuristic that speeds up the search as much as possible within the current memory restrictions.

### Selecting the metric

The solver can be set up to use one of two metrics to determine the length of an algorithm.

In **face turn metric** each turn of a face counts as one move, whether it be a fifth turn (72°) or a double fifth turn (144°).

In **fifth turn metric** each fifth turn of a face counts as one move. Turning a face two fifths (144°) now counts as two moves.

![Metrics](https://github.com/jazzthief81/llminxsolver/raw/master/docs/images/metrics.png "Metrics")

You will find that searching in fifth turn metric will return more ergonomic algorithms, while searching in face turn metric will often yield awkward solutions that have almost nothing but double fifth turns in them.

### Ignoring pieces

You can choose to

- ignore the **position** of all **corners**,
- ignore the **position** of all **edges**,
- ignore the **orientation** of all **corners** or
- ignore the **orientation** of all **edges**.

Any of the four settings can be toggled on and off independently and all combinations are supported by the solver. The diagram on the top-left will reflect these settings by greying out the stickers that are ignored when ignoring the position of corners or edges. When ignoring the orientation, a checkerboard pattern will indicate that a sticker can have any of two or three colors.

![Solve only edge orientation](https://github.com/jazzthief81/llminxsolver/raw/master/docs/images/solve_edge_orientation.png "Solve only edge orientation")
![Solve only orientation](https://github.com/jazzthief81/llminxsolver/raw/master/docs/images/solve_orientation.png "Solve only orientation")
![Ignore edge position](https://github.com/jazzthief81/llminxsolver/raw/master/docs/images/ignore_edge_position.png "Ignore edge position")
![Ignore corner orientation](https://github.com/jazzthief81/llminxsolver/raw/master/docs/images/ignore_corner_orientation.png "Ignore corner orientation")

### Starting the search

To start the search, click the **Solve** button.

The solver will try to find solutions of increasing length: it will start by searching for all 1 move solutions, then all 2 move solutions and so on. It will exhaustively find all algorithms that solve the starting position and not just the optimal ones.

All found solutions are formatted and printed in the box in the bottom portion of the window. The length in face turn metric and fifth turn metric are put next to the algorithm in between brackets.

Algorithms can be selected and then copied and pasted using the standard hotkeys CTRL-C and CTRL-V (or cmd-C and cmd-V on the Mac). If you want to check the algorithms while the search is running, you can disable the **Follow messages** checkbox. This will prevent the output box from scrolling down each time a new solution is found so that you can read the output at your own pace.

![Solutions](https://github.com/jazzthief81/llminxsolver/raw/master/docs/images/solutions.png "Solutions")

If, however, you want to see all new solutions as they’re found you can re-enable the **Follow messages** checkbox again.

The window can also be resized and the output box will automatically claim that extra space so that you can work more efficiently.

Searching will be fast for the first few depths and will gradually get slower as it tries to look for longer solutions. If a certain depth takes longer than a few seconds to complete, the application will show you a progress indicator at the bottom of the window.

While the search is ongoing, you can stop the search at any time by pressing the **Cancel** button.

### Limiting the search depth

If you want to solver to stop automatically after a certain depth, you can check the **Limit** option in the **Search depth** pane and enter a maximum number of moves. When the **Limit** option is disabled, the search will go on forever until the user has pressed **Cancel**.

### Pruning tables

When you run the search for the first time in a certain metric and with certain allowed faces, it will generate pruning tables for speeding up the search. These tables will take some time to build and are written to disk after completion. After the pruning tables are generated, the actual search kicks in.

The next time you start a search with the same combination of settings (metric and allowed faces), the application will read the table from disk and instantly start the search.

If building the pruning tables take too much time, you can interrupt it by pressing **Cancel**. The solver will keep the tables that were already finished and pick up where it left off the next time you start a search.

This table shows the required disk space and gives you an idea of how much time it approximately takes to build them for each setting.

| Allowed moves | Time to generate* | Disk space |
| ------------- | ----------------- | ---------- |
| R, U          | 1 minute          | 42 MB      |
| R, U, L       | 1 minute          | 19 MB      |
| R, U, F       | 10 minutes        | 268 MB     |
| R, U, F, L    | 15 minutes        | 228 MB     |
| R, U, F, L, B | 50 minutes        | 317 MB     |

<small>*These times were measured on an Intel® Core™2 Duo T7700 running at 2.4 Ghz.</small>

Keep in mind that these tables need to be generated twice: once for fifth turn metric and once for face turn metric. The total disk space required for storing all pruning tables is **1.7 GB**.

### Future enhancements

Here are some improvements that I have in mind for future versions:

- Add support for other combinations of faces (like R, U, D) and more simultaneous faces (like R, U, F, L, B and the ’second’ B face).
- Implement a more integrated search that runs several modes in parallel (for instance all modes that use 3 faces).
- Have the ability to ignore the position and orientation of any individual piece and not just of all corners or all edges.
- Before building a set of pruning tables, inform the user of the required disk space and the estimated time for generating them and let the user confirm.
- Allow smaller and/or less pruning tables so that the application remains usable (but slower) on machines with less memory.
- Allow bigger pruning tables that will make the search even faster on machines with more memory.
- Add center pieces to the diagram so that you can see more clearly which pieces belongs where.
- Add an option to show arrows on the diagram so that you can see the cycles.
