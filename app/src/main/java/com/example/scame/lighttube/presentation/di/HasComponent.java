package com.example.scame.lighttube.presentation.di;

/**
 * interface representing a contract for clients that contain a component for dependency injection
 */
public interface HasComponent<C> {
    C getComponent();
}
