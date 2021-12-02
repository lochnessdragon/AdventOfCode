def day12_2():
    with open("test.txt", "r") as file:
        data = file.readlines()
    data = [(i[:1], int(i[1:-1])) for i in data]
    print(data[0])


    # (N/S, E/W) Positive = North, East. Negative = South, West
    waypoint = [1, 10]
    curr_pos = [0, 0]
    delta_ns = {"N": 1, "S": -1, "E": 0, "W": 0}
    delta_ew = {"N": 0, "S": 0, "E": 1, "W": -1}
    for drxn, val in data:
        if drxn == "F":
            curr_pos = [curr_pos[0] + waypoint[0] * val, curr_pos[1] + waypoint[1] * val]
        elif drxn == "L":
            ccw = {90: [waypoint[1], -waypoint[0]], 180: [-waypoint[0], -waypoint[1]], 270: [-waypoint[1], waypoint[0]]}
            waypoint = ccw[val]
        elif drxn == "R":
            cw = {90: [-waypoint[1], waypoint[0]], 180: [-waypoint[0], -waypoint[1]], 270: [waypoint[1], -waypoint[0]]}
            waypoint = cw[val]
        else:
            waypoint = [waypoint[0] + delta_ns[drxn] * val, waypoint[1] + delta_ew[drxn] * val]

    return abs(curr_pos[0]) + abs(curr_pos[1])