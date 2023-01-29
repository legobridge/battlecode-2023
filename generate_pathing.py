import sys
from pathlib import Path

LIMIT = 3
RADIUS = 13
SMALLER_RADIUS = 4
# Note: Amps should be (34, 20) but we've reduced for bytecode reasons

def encode(x, y):
    return (x+LIMIT) + 15*(y+LIMIT)


DIRECTIONS = {
    (1, 0): 'Direction.EAST',
    (-1, 0): 'Direction.WEST',
    (0, 1): 'Direction.NORTH',
    (0, -1): 'Direction.SOUTH',
    (1, 1): 'Direction.NORTHEAST',
    (-1, 1): 'Direction.NORTHWEST',
    (1, -1): 'Direction.SOUTHEAST',
    (-1, -1): 'Direction.SOUTHWEST',
}

def dist(x, y):
    return x*x + y*y

def gen_constants():
    out = f""""""
    for x in range(-LIMIT, LIMIT + 1):
        for y in range(-LIMIT, LIMIT + 1):
            if dist(x, y) <= RADIUS:
                out += f"""
    static MapLocation l{encode(x,y)}; // location representing relative coordinate ({x}, {y})
    static int dwalk{encode(x,y)}; // shortest distance to location from current location if the last move was a walk
    static int dcur{encode(x,y)}; // shortest distance to location from current location if the last move was a current
    static Direction dirwalk{encode(x,y)}; // best direction to take now if the last move was a walk
    static Direction dircur{encode(x,y)}; // best direction to take now if the last move was a current
    static boolean p{encode(x,y)}; // is the location passable
    static Direction c{encode(x,y)}; // direction of the current at the location
"""
    return out

def sign(x):
    if x > 0:
        return 1
    if x < 0:
        return -1
    return 0

def gen_init():
    out = f"""
        l{encode(0,0)} = rc.getLocation();
        dwalk{encode(0,0)} = 0;
        dcur{encode(0,0)} = 99999;
        dirwalk{encode(0,0)} = Direction.CENTER;
        dircur{encode(0,0)} = Direction.CENTER;

        if (l{encode(0,0)}.isAdjacentTo(target)) {{
            return l{encode(0,0)}.directionTo(target);
        }}

        MapInfo mi{encode(0,0)} = rc.senseMapInfo(l{encode(0,0)});
        p{encode(0,0)} = true;
        c{encode(0,0)} = mi{encode(0,0)}.getCurrentDirection();

        initRest();"""
    return out

def gen_initRest():
    out = ""
    for r2 in range(1, RADIUS+1):
        for x in range(-LIMIT, LIMIT + 1):
            for y in range(-LIMIT, LIMIT + 1):
                if dist(x, y) == r2:
                    out += f"""
        l{encode(x,y)} = l{encode(x - sign(x), y - sign(y))}.add({DIRECTIONS[(sign(x), sign(y))]}); // ({x}, {y}) from ({x - sign(x)}, {y - sign(y)})
        dwalk{encode(x,y)} = 99999;
        dcur{encode(x,y)} = 99999;
        dirwalk{encode(x,y)} = null;
        dircur{encode(x,y)} = null;
        p{encode(x,y)} = true;
        c{encode(x,y)} = Direction.CENTER;
    """
    return out

def gen_bfs():
    visited = set([encode(0,0)])
    out = f"""
"""
    for r2 in range(1, RADIUS+1):
        for x in range(-LIMIT, LIMIT + 1):
            for y in range(-LIMIT, LIMIT + 1):
                if dist(x, y) == r2:
                    out += f"""
        if (rc.onTheMap(l{encode(x,y)})) {{ // check ({x}, {y})
            if (rc.canSenseLocation(l{encode(x,y)})) {{
                MapInfo mi{encode(x,y)} = rc.senseMapInfo(l{encode(x,y)});
                p{encode(x,y)} = mi{encode(x,y)}.isPassable();
                c{encode(x,y)} = mi{encode(x,y)}.getCurrentDirection();
            }}
            if (p{encode(x,y)}) {{"""
                    indent = ""
#                     if r2 <= 2:
#                         out += f"""
#             if (!rc.isLocationOccupied(l{encode(x,y)})) {{ """
#                         indent = "    "
                    dxdy = [(dx, dy) for dx in range(-1, 2) for dy in range(-1, 2) if (dx, dy) != (0, 0) and dist(x+dx,y+dy) <= RADIUS]
                    dxdy = sorted(dxdy, key=lambda dd: dist(x+dd[0], y+dd[1]))
                    for dx, dy in dxdy:
                        if encode(x+dx, y+dy) in visited:
                            out += f"""
                // from ({x+dx}, {y+dy})
                if (c{encode(x+dx,y+dy)} != Direction.CENTER) {{ // there was a current at the previous location
                    if (l{encode(x+dx,y+dy)}.add(c{encode(x+dx,y+dy)}).equals(l{encode(x,y)})) {{ // the current flows into this location
                        if (dwalk{encode(x+dx,y+dy)} < dcur{encode(x,y)}) {{ // I walked onto the previous location so I can reach this one at the end of the turn
                            dcur{encode(x,y)} = dwalk{encode(x+dx,y+dy)};
                            dircur{encode(x,y)} = {DIRECTIONS[(-dx, -dy)] if (x+dx,y+dy) == (0, 0) else f'dirwalk{encode(x+dx,y+dy)}'};
                        }}
                        if (dcur{encode(x+dx,y+dy)} + 1 < dcur{encode(x,y)}) {{ // I can wait on the current till the end of the turn
                            dcur{encode(x,y)} = dcur{encode(x+dx,y+dy)} + 1;
                            dircur{encode(x,y)} = {DIRECTIONS[(-dx, -dy)] if (x+dx,y+dy) == (0, 0) else f'dircur{encode(x+dx,y+dy)}'};
                        }}
                    }}
                }}
                else {{ // there was no current at the previous location
                    if (dwalk{encode(x+dx,y+dy)} + 1 < dwalk{encode(x,y)}) {{ // I can walk twice in a row since there was no current
                        dwalk{encode(x,y)} = dwalk{encode(x+dx,y+dy)} + 1;
                        dirwalk{encode(x,y)} = {DIRECTIONS[(-dx, -dy)] if (x+dx,y+dy) == (0, 0) else f'dirwalk{encode(x+dx,y+dy)}'};
                    }}
                }}
                if (dcur{encode(x+dx,y+dy)} + 1 < dwalk{encode(x,y)}) {{ // in all cases, I can walk off the previous location if I arrived there via current
                    dwalk{encode(x,y)} = dcur{encode(x+dx,y+dy)} + 1;
                    dirwalk{encode(x,y)} = {DIRECTIONS[(-dx, -dy)] if (x+dx,y+dy) == (0, 0) else f'dircur{encode(x+dx,y+dy)}'};
                }}
                """
#             {indent}if (d{encode(x,y)} > d{encode(x+dx,y+dy)}) {{ // from ({x+dx}, {y+dy})
#                 {indent}d{encode(x,y)} = d{encode(x+dx,y+dy)};
#                 {indent}dirwalk{encode(x,y)} = {DIRECTIONS[(-dx, -dy)] if (x+dx,y+dy) == (0, 0) else f'dirwalk{encode(x+dx,y+dy)}'};
#             {indent}}}"""
#                     out += f"""
#             {indent}d{encode(x,y)} += 1;"""
#                     if r2 <= 2:
#                         out += f"""
#             }}"""
                    visited.add(encode(x,y))
                    out += f"""
              }}
        }}
"""
    return out

def gen_selection():
    out = f"""
        int target_dx = target.x - l{encode(0,0)}.x;
        int target_dy = target.y - l{encode(0,0)}.y;
        switch (target_dx) {{"""
    for tdx in range(-LIMIT, LIMIT + 1):
        if tdx**2 <= RADIUS:
            out += f"""
                case {tdx}:
                    switch (target_dy) {{"""
            for tdy in range(-LIMIT, LIMIT + 1):
                if dist(tdx, tdy) <= RADIUS:
                    out += f"""
                        case {tdy}:
                            return (dwalk{encode(tdx, tdy)} < dcur{encode(tdx, tdy)}) ? dirwalk{encode(tdx, tdy)} : dircur{encode(tdx, tdy)}; // destination is at relative location ({tdx}, {tdy})"""
            out += f"""
                    }}
                    break;"""
    out += f"""
        }}

        Direction ans = null;
        double bestScore = 0;
        double currDist = Math.sqrt(l{encode(0,0)}.distanceSquaredTo(target));
        """
    for x in range(-LIMIT, LIMIT + 1):
        for y in range(-LIMIT, LIMIT + 1):
            if SMALLER_RADIUS < dist(x, y) <= RADIUS: # on the edge of the RADIUS RADIUS
                out += f"""
        if (dwalk{encode(x,y)} < dcur{encode(x,y)}) {{
            double score{encode(x,y)} = (currDist - Math.sqrt(l{encode(x,y)}.distanceSquaredTo(target))) / dwalk{encode(x,y)};
            if (score{encode(x,y)} > bestScore) {{
                bestScore = score{encode(x,y)};
                ans = dirwalk{encode(x,y)};
            }}
        }}
        else {{
            double score{encode(x,y)} = (currDist - Math.sqrt(l{encode(x,y)}.distanceSquaredTo(target))) / dcur{encode(x,y)};
            if (score{encode(x,y)} > bestScore) {{
                bestScore = score{encode(x,y)};
                ans = dircur{encode(x,y)};
            }}
        }}
"""
    return out

def gen_print():
    out = f"""
        // System.out.println("LOCAL DISTANCES:");"""
    for y in range(LIMIT, -LIMIT - 1, -1):
        if y**2 <= RADIUS:
            out += f"""
        // System.out.println("""
            for x in range(-LIMIT, LIMIT + 1):
                if dist(x, y) <= RADIUS:
                    out += f""""\\t" + dwalk{encode(x,y)} + """
                else:
                    out += f""""\\t" + """
            out = out[:-3] + """);"""
    out += f"""
        // System.out.println("DIRECTIONS:");"""
    for y in range(LIMIT, -LIMIT - 1, -1):
        if y**2 <= RADIUS:
            out += f"""
        // System.out.println("""
            for x in range(-LIMIT, LIMIT + 1):
                if dist(x, y) <= RADIUS:
                    out += f""""\\t" + dirwalk{encode(x,y)} + """
                else:
                    out += f""""\\t" + """
            out = out[:-3] + """);"""
    return out

def gen_full(bot):
    out_file = Path('./src/') / bot / f'BFSPathing.java'
    with open(out_file, 'w') as f:
        f.write(f"""// Inspired by https://github.com/IvanGeffner/battlecode2021/blob/master/thirtyone/BFSMuckraker.java.
package {bot};

import battlecode.common.*;

public class BFSPathing {{

    RobotController rc;

{gen_constants()}

    public BFSPathing(RobotController rc) {{
        this.rc = rc;
    }}

    public Direction getBestDirection(MapLocation target) throws GameActionException {{
{gen_init()}
{gen_bfs()}
{gen_print()}
{gen_selection()}

        return ans;
    }}

    private void initRest() {{
    {gen_initRest()}
    }}
}}
""")

if __name__ == '__main__':
    gen_full(sys.argv[1])