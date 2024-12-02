with open('data.txt', 'r') as file:
    inp = file.read()

grid = list(map(list,inp.split("\n")))

def slide(grid):
    n = len(grid)
    m = len(grid[0])
    for j in range(m):
        ci = 0
        for i in range(n):
            if grid[i][j] == "#":
                ci = i + 1
            if grid[i][j] == "O":
                grid[i][j] = "."
                grid[ci][j] = "O"
                ci += 1

def get_score(grid):
    n = len(grid)
    ans = 0
    for i in range(n):
        ans += (n - i) * grid[i].count("O")
    return ans

def rotate(grid):
    n = len(grid)
    m = len(grid[0])
    new_grid = [["." for j in range(n)] for i in range(m)]
    for i in range(n):
        for j in range(m):
            new_grid[j][n - i - 1] = grid[i][j]
    return new_grid

def to_str(grid):
    return "".join(["".join(grid[i]) for i in range(len(grid))])

d = {}
goal = 1000000000
idx = 1
while True:
    for j in range(4):
        slide(grid)
        grid = rotate(grid)
    x = to_str(grid)
    if x in d:
        cyclen = idx - d[x][0]
        for a,b in d.values():
            if a >= d[x][0] and a % cyclen == goal % cyclen:
                print(b)
        break
    d[x] = (idx, get_score(grid))
    idx += 1