class Node:
    def __init__(self, state, parentNode = None, numOfTowers=3, numOfDisks=3):
        self.state = state
        self.parentNode = parentNode
        assert(len(self.state) == numOfTowers)
        for tower in self.state: assert(len(tower) == numOfDisks)
        assert isinstance(self.index, int)
    
    def __repr__(self):
        return "node: " + self.index + " state: " + repr(self.state)

    def isValidState(self) -> bool: pass

    def getNeighbors(self) -> set:
        # structure of state:
        # [[Tower1],[Tower2],[Tower3]]
        # Tower1 = [Disk in slot 1, Disk in slot 2, Disk in slot 3]
        possibleNeighbors=set()
        for tower in self.state:
            topDisk = self.getTopDisk(tower)
            for recieveingTower in self.state:
                if recieveingTower == tower:
                    continue
                

    def getTopDisk(tower):
        for i in range(len(tower)).reverse():
            if tower[i] != None:
                return tower[i]
        return None

    def placeDisk(tower, disk):
        for i in range(len(tower)).reverse():
            if i == 0 and tower[i] == None:
                tower[i] = disk
            if tower[i-1] <= disk:
                return False
            if tower[i-1] != None and tower[i] == None:
                tower[i] == disk
            
        return tower

    def isTerminalNode(self) -> bool: pass

    def __eq__(self, other) -> bool:
        return other.state == self.state
    