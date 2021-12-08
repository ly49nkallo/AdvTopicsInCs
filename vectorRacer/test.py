from json import load
import sys
from surfaceTypes import *
import Game
import numpy as np
import matplotlib.pyplot as plt
import time
import State

DATAPATH = 'OvalTrack.json'
def getTrajectory(position, state2) -> set:
        #assert isinstance(self, State) and isinstance(state2, tuple)
        
        trajectory = set()
        # m = dy/dx
        try:
            slope = (position[1]-state2[1])/(position[0]-state2[0])
        except ZeroDivisionError:
            slope = 1
        yint = position[1] - slope*position[0]
        if position[0] < state2[0]:
            for j in range(position[0],state2[0],1):
                trajectory.add((j, round(slope*j + yint)))
        else:
             for j in range(position[0],state2[0],-1):
                trajectory.add((j, round(slope*j + yint)))
        return trajectory

if __name__ == '__main__':
    with open(DATAPATH, 'r') as f:
        data = load(f)
    cells = data['cells']
    cellsX = data['cellsX']
    cellsY = data['cellsY']
    
    im = np.zeros((cellsX, cellsY))
    '''
    fig = plt.figure(figsize=(5,5))
    ax = fig.add_subplot(111)
    ax.set_title('map')
    plt.imshow(im)
    ax.set_aspect('equal')
    cax = fig.add_axes([0.12, 0.1, 0.78, 0.8])
    cax.get_xaxis().set_visible(False)
    cax.get_yaxis().set_visible(False)
    cax.patch.set_alpha(0)
    cax.set_frame_on(False)
    plt.show(block=True)
    '''
    cells = {(10,10), (15,25)}
    for cell in cells:
        im[cell[1], cell[0]] = 10
    for i in getTrajectory((10,10), (15,25)):
        im[i[1], i[0]] += 1
    


    fig = plt.figure(figsize=(5,5))
    ax = fig.add_subplot(111)
    ax.set_title(str(data['trackName']))
    plt.imshow(im)
    ax.set_aspect('equal')
    cax = fig.add_axes([0.12, 0.1, 0.78, 0.8])
    cax.get_xaxis().set_visible(False)
    cax.get_yaxis().set_visible(False)
    cax.patch.set_alpha(0)
    cax.set_frame_on(False)
    plt.show(block=True)
    with open('steps.vrdat', 'w', encoding='UTF-8') as f:
        for i in range(len(foundPath) - 1):
            ax, ay = foundPath[i+1].velocity[0] - foundPath[i].velocity[0], foundPath[i+1].velocity[1] - foundPath[i].velocity[1]
            f.write(str(ax) + ' ' + str(ay) + '\n')

    