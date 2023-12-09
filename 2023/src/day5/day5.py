#!/usr/bin/env python3
import re
from functools import reduce

almanac = open("data.txt").read().rstrip()
entries = almanac.split("\n\n")
seeds = list(map(int, re.findall(r"\d+", entries[0])))

maps = []
for mp in entries[1:]:
    m = []
    for line in mp.split("\n")[1:]:
        e, b, d = map(int, re.findall(r"\d+", line))
        m.append([b, b + d - 1, e - b])
    maps.append(sorted(m))

def lookup(s, m):
    for b, e, d in m:
        if   s > e: continue
        elif s < b: return s
        else:       return s + d
    return s

def lookup2(s, t, m):
    rs = []
    # begin, end, delta
    for b, e, d in m:
        #                          b.........e  [s-t]
        if s>e or t<b: # or: [s-t] b.........e
            continue
        if s < b: #            [s--b-----?t].e  ?t]
            rs += [(s, b - 1), (b + d, min(e, t) + d)]
        else: #                    b.[s--?t].e  ?t]
            rs += [(s + d, min(e, t) + d)]

        if e > t: return rs #      b.[s--t]..e  
        s = e
    if not rs: rs = [(s, t)]
    return rs

def process2(p):
    r = [(p[0], p[0] + p[1])]
    for m in maps:
        rs = []
        for s, t in r:
            rs += lookup2(s, t, m)
        r = rs
    return min(rs)[0]

locations  = [reduce(lookup, maps, s) for s in seeds]
locations2 = [process2((seeds[i:i + 2])) for i in range(0, len(seeds), 2)]

print(f"Part1: {min(locations)}\nPart2: {min(locations2)}")