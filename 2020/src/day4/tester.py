import fileinput

ports = []
port = set()

for line in fileinput.input():
    line = line.strip()
    if not line:
        ports.append(port)
        port = set()
    else:
        pairs = line.split(' ')
        for pair in pairs:
            key = pair.split(':')[0]
            port.add(key)

def valid(p):
    p = set(p)
    p.add('cid')
    p.remove('cid')
    return len(p) == 7

print(len(list(x for x in ports if valid(x))))