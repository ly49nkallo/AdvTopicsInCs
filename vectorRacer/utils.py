
'''
Takes coordinate tuple and returns it's corresponding index in the map
'''
def IX(coord:tuple, cellsX:int) -> int:
    try:
        assert len(coord) == 2
    except AssertionError:
        raise NameError("input tuple is not the right shape")

    return coord[1] * cellsX + coord[0]

def xCoord(index, cellsX):
    return index % cellsX

def yCoord(index, cellsX):
    return index / cellsX 

'''
Takes index and returns it's corresponding logical coordinate
'''
def Coord(index, cellsX):
    return (index % cellsX, index // cellsX)