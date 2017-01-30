package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {

    public static final int SIZE = 10;

    public static ArrayList<ArrayList<Room>> createRooms() {
        ArrayList<ArrayList<Room>> rooms = new ArrayList<>();

        for (int row = 0; row < SIZE; row++) {
            ArrayList<Room> roomRow = new ArrayList<>();
            for (int col = 0; col < SIZE; col++) {
                roomRow.add(new Room(row, col));
            }
            rooms.add(roomRow);
        }
        return rooms;

    }

    public static ArrayList<Room> possibleNeighbors(ArrayList<ArrayList<Room>> rooms, int row, int col) {
        ArrayList<Room> neighbors = new ArrayList<>();

        if (row > 0) neighbors.add(rooms.get(row - 1).get(col));
        if (row < SIZE - 1) neighbors.add(rooms.get(row + 1).get(col));
        if (col > 0) neighbors.add(rooms.get(row).get(col - 1));
        if (col < SIZE - 1) neighbors.add(rooms.get(row).get(col + 1));

        neighbors = neighbors.stream()
                .filter(room -> {
                    return !room.wasVisited;
                })
                .collect(Collectors.toCollection(ArrayList<Room>::new));


        return neighbors;
    }

    public static Room randomNeighbor(ArrayList<ArrayList<Room>> rooms, int row, int col) {
        ArrayList<Room> neighbors = possibleNeighbors(rooms, row, col);

        if (neighbors.size() > 0) {
            Random r = new Random();
            int index = r.nextInt(neighbors.size());
            return neighbors.get(index);
        }

        return null;
    }

    public static void tearDownWall(Room oldRoom, Room newRoom) {
        // going up
        if (newRoom.row < oldRoom.row) {
            newRoom.hasBottom = false;
        }
        // going down
        else if (newRoom.row > oldRoom.row) {
            oldRoom.hasBottom = false;
        }
        // going left
        else if (newRoom.col < oldRoom.col) {
            newRoom.hasRight = false;
        }
        // going right
        else if (newRoom.col > oldRoom.col) {
            oldRoom.hasRight = false;
        }
    }
    public static boolean createMaze(ArrayList<ArrayList<Room>> rooms, Room room) {
        room.wasVisited = true;
        Room nextRoom = randomNeighbor(rooms, room.row, room.col);
        if (nextRoom == null) {
            if(!mazeEnd(rooms)){
                room.isEnd = true;
            }
            return false;
        }

        tearDownWall(room, nextRoom);

        while (createMaze(rooms, nextRoom));

        return true;
    }

    static boolean mazeEnd (ArrayList<ArrayList<Room>>rooms){
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j< SIZE; j++){
                if(rooms.get(i).get(j).isEnd) return true;
            }
        }
        return false;
    }




    public static void main(String[] args) {
        ArrayList<ArrayList<Room>> rooms = createRooms();
        Room startRoom = rooms.get(0).get(0);
        startRoom.isStart = true;
        createMaze(rooms, rooms.get(0).get(0));

        for (ArrayList<Room> row : rooms) {
            System.out.print(" _");
        }
        System.out.println();
        for (ArrayList<Room> row : rooms) {
            System.out.print("|");
            for (Room room : row) {
                if (room.isStart){
                    System.out.print("o");
                } else if (room.isEnd){
                    System.out.print("x");
                } else if (room.hasBottom) {
                    System.out.print("_");
                } else {
                    System.out.print(" ");
                }

                if (room.hasRight) {
                    System.out.print("|");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

}




