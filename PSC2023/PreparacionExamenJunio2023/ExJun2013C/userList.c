//
// Created by in4p on 6/14/23.
//
#include "userList.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

T_user * createUser(char *name, int uid, char *dir) {
    T_user *user = (T_user *)malloc(sizeof(T_user));
    user->uid_ = uid;
    user->userName_ = (char *)malloc(strlen(name) + 1);  // +1 para el carácter nulo final
    user->homeDirectory_ = (char *)malloc(strlen(dir) + 1);  // +1 para el carácter nulo final
    strcpy(user->userName_, name);
    strcpy(user->homeDirectory_, dir);
    user->nextUser_ = NULL;
    user->previousUser_ = NULL;
    return user;
}

T_userList createUserList() {
    T_userList list;
    list.head_ = NULL;
    list.tail_ = NULL;
    return list;
}

int addUser(T_userList *list, T_user*user) {
    T_user *current = list->head_;
    while(current != NULL) {
        if(strcmp(current->userName_, user->userName_) == 0 || current->uid_ == user->uid_) {
            return 0; //usuario duplicado
        }
        current = current->nextUser_;
    }

    //Agregar el usuario en la cabeza de la lista
    if (list->head_ == NULL) {
        //Caso de lista vacia
        list->head_ = user;
        list->tail_ = user;
    }else{
        user->nextUser_ = list->head_;
        list->head_->previousUser_ = user;
        list->head_ = user;
    }
    return 1;
}

int getUid(T_userList list, char *userName) {
    T_user *current = list.head_;
    while (current != NULL) {
        if(strcmp(current->userName_, userName) == 0) {
            return current->uid_;
        }
        current = current->nextUser_;
    }
    return -1;
}

int deleteUser(T_userList *list, char* userName) {
    T_user *current = list->head_;

    while (current != NULL) {
        if(strcmp(current->userName_, userName) == 0) {
            if(current == list->head_) {
                //Caso de eliminacion de usuario en la cabeza
                list->head_ = current->nextUser_;
                if(list->head_ != NULL) {
                    list->head_->previousUser_ = NULL;
                }else{
                    list->tail_ = NULL;  // La lista queda vacía
                }
            }else if(current == list->tail_) {
                // Caso de eliminación de usuario en la cola de la lista
                list->tail_ = current->previousUser_;
                if(list->tail_ != NULL) {
                    list->tail_->nextUser_ = NULL;
                }else{
                    list->head_ = NULL;  // La lista queda vacía
                }
            }else{
                // Caso de eliminación de usuario en el medio de la lista
                current->previousUser_->nextUser_ = current->nextUser_;
                current->nextUser_->previousUser_ = current->previousUser_;
            }
            free(current);
            return 0; //usuario eliminado con exito
        }
    }
    return -1;
}

void printUserList(T_userList list, int reverse) {
    if(list.head_ == NULL){
        printf("La lista esta vacia\n");
        return;
    }

    T_user *current;
    if(reverse) {
        printf("Impresión de la lista de usuarios en orden inverso:\n");
        current = list.tail_;
        while(current != NULL) {
            printf("Nombre: %s, UID: %d, Directorio home: %s\n",
                   current->userName_, current->uid_, current->homeDirectory_);
            current = current->previousUser_;
        }
    }else {
        printf("Impresión de la lista de usuarios en orden normal:\n");
        current = list.head_;
        while (current != NULL) {
            printf("Nombre: %s, UID: %d, Directorio home: %s\n",
                   current->userName_, current->uid_, current->homeDirectory_);
            current = current->nextUser_;
        }
    }
}
