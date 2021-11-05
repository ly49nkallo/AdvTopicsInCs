import copy

class Node:
    def __init__(self, state=[[3,2,1],[0,0,0],[0,0,0]], parentNode = None, numOfTowers=3, numOfDisks=3):
        self.state = state
        self.parentNode = parentNode
        self.numOfDisks = numOfDisks
        self.numOfTowers = numOfTowers
        assert(len(self.state) == numOfTowers)
        for tower in self.state: assert(len(tower) == numOfDisks)
        # assert isinstance(self.index, int)
    
    def __repr__(self):
        return " state: " + repr(self.state)

    def isValidState(self) -> bool: pass

    def getNeighbors(self) -> set:
        # structure of state:
        # [[Tower1],[Tower2],[Tower3]]
        # Tower1 = [Disk in slot 1, Disk in slot 2, Disk in slot 3]
        possibleNeighbors=set()
        print("selfstate", self.state)
        for tower in range(len(self.state)):
            print("state" , self.state[tower])
            topDisk = self.getTopDisk(tower)
            print("top disk:", topDisk)
            for i in range(len(self.state)):
                if tower == i:
                    continue
                if topDisk == None:
                    continue
                newState = self.placeDisk(i, topDisk)
                newTower = newState[i]
                print("new tower:", newTower)
                if not newTower: 
                    #print("r")
                    continue
                newState = copy.deepcopy(self.state)
                newState[i] = newTower
                newState[tower] = self.removeTopDisk(tower)
                print("new state:", newState)
                possibleNeighbors.add(Node(state = newState))
        return possibleNeighbors

    def getTopDisk(self, tower_index):
        tower = self.state.copy()[tower_index]
        # print (list(reversed(list(range(len(tower))))))
        for i in reversed(list(range(self.numOfDisks))):
            if tower[i] != 0:
                return tower[i]
        return None

    def removeTopDisk(self,tower_index):
        tower = self.state.copy()[tower_index]
        tower[tower.index(self.getTopDisk(tower_index))] = 0
        return tower

    def placeDisk(self, tower_index, disk):
        tower = self.state.copy()[tower_index]
        print("tower: ", tower)
        for i in reversed(list(range(self.numOfDisks))):
            if i == 0 and tower[i] == 0:
                tower[i] = disk
            if tower[i-1] != 0 and tower[i-1] < disk:
                return False
            if tower[i-1] != 0 and tower[i] == 0:
                tower[i] == disk
        newState = self.state.copy()
        newState[tower_index] = tower
        print(newState)
        return newState

    def isTerminalNode(self) -> bool: pass

    def __eq__(self, other) -> bool:
        return other.state == self.state

    def __hash__(self):
        hash = 0
        n = 0
        # even more list comprehention :)
        flat_list = [item for sublist in self.state for item in sublist]
        for item in flat_list:
            hash += item * (self.numOfDisks ** n)
            n += 1
        return hash
    
    def __len__(self):
        return len(self.state)