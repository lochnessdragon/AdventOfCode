import dataclasses
import timeit


@dataclasses.dataclass(frozen=True)
class Instr:
    action: str
    value: int


@dataclasses.dataclass(frozen=True)
class Coords:
    x: int
    y: int

    def manhattan_dist(self):
        return abs(self.x) + abs(self.y)


MOVES = {
    'N': Coords(0, 1),
    'E': Coords(1, 0),
    'S': Coords(0, -1),
    'W': Coords(-1, 0),
}

TURNS = {'L': -1, 'R': 1}


class Ship:

    def __init__(self, instrs_input=None):
        instrs_input = instrs_input if instrs_input is not None else self._read_file()
        self.instructions = [self._preprocess(line) for line in instrs_input.splitlines()]
        self.position = Coords(0, 0)
        self.relative_waypoint = Coords(10, 1)

    @staticmethod
    def _read_file():
        with open('input.txt') as f:
            return f.read()

    @staticmethod
    def _preprocess(line):
        act, val = line[:1], line[1:]
        return Instr(act, int(val))


def new_pos(pos, direction, val):
    x_val = direction.x * val
    y_val = direction.y * val
    return Coords(pos.x + x_val, pos.y + y_val)


def rotate_90_clockwise(pos):
    return Coords(pos.y, pos.x * -1)


def process_with_theoretical_actions(ship):
    ship_pos = ship.position
    ship_dir = 'E'
    moves = list(MOVES.keys())
    for instr in ship.instructions:
        if instr.action in MOVES or instr.action == 'F':
            move_act = ship_dir if instr.action == 'F' else instr.action
            direction = MOVES[move_act]
            ship_pos = new_pos(ship_pos, direction, instr.value)
        elif instr.action in TURNS:
            turn_no = (instr.value // 90) * TURNS[instr.action]
            move_no = (moves.index(ship_dir) + turn_no) % 4
            ship_dir = moves[move_no]
        else:
            raise Exception
    return ship_pos


def process_with_actual_actions(ship):
    ship_pos = ship.position
    waypoint_pos = ship.relative_waypoint
    for instr in ship.instructions:
        if instr.action == 'F':
            ship_pos = new_pos(ship_pos, waypoint_pos, instr.value)
        elif instr.action in MOVES:
            direction = MOVES[instr.action]
            waypoint_pos = new_pos(waypoint_pos, direction, instr.value)
        elif instr.action in TURNS:
            turn_no = (instr.value // 90) * TURNS[instr.action]
            clockwise_turn_no = turn_no % 4
            for _ in range(clockwise_turn_no):
                waypoint_pos = rotate_90_clockwise(waypoint_pos)
        else:
            raise Exception
    return ship_pos


def main():
    ship = Ship()
    final_theoretical_pos = process_with_theoretical_actions(ship)
    print(
        'Manhattan Distance of theoretical final position from current position: ',
        final_theoretical_pos.manhattan_dist()
    )
    final_actual_pos = process_with_actual_actions(ship)
    print(
        'Manhattan Distance of actual final position from current position: ',
        final_actual_pos.manhattan_dist()
    )


if __name__ == '__main__':
    print(f'Completed in {timeit.timeit(main, number=1)} seconds')