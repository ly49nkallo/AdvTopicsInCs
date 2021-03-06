from json import load
import sys
from surfaceTypes import *
import Game
import numpy as np
import matplotlib.pyplot as plt
import time

DATAPATH = 'OvalTrack.json'

if __name__ == '__main__':
    with open(DATAPATH, 'r') as f:
        data = load(f)
    cells = data['cells']
    cellsX = data['cellsX']
    cellsY = data['cellsY']
    
    im = list()
    for i in range(0,len(cells)-1,cellsX): 
        im.append(cells[i:i+cellsX])
    im = np.array(im)
    im[33][31:36] = 1
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
    g = Game.Game(im, cellsX, cellsY)
    time.sleep(1)
    foundPath = g.AStar()
    cookiepath = []
    for node in foundPath:
        im[node.position[1]][node.position[0]] = 10
    for i in range(len(foundPath)-1):
        node = foundPath[i]
        nextNode = foundPath[i+1]
        for n in node.getTrajectory(nextNode.position):
            im[n[1]][n[0]] = 9
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

    