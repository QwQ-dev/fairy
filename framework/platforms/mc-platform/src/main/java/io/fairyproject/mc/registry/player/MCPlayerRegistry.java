/*
 * MIT License
 *
 * Copyright (c) 2022 Fairy Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.fairyproject.mc.registry.player;

import io.fairyproject.mc.MCPlayer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public interface MCPlayerRegistry {

    ReentrantLock JOIN_QUIT_LOCK = new ReentrantLock();

    @NotNull MCPlayer findPlayerByUuid(@NotNull UUID uuid);

    @NotNull MCPlayer findPlayerByName(@NotNull String name);

    @NotNull default MCPlayer findPlayerByPlatformPlayer(@NotNull Object platformPlayer) {
        return getByPlatform(platformPlayer);
    }

    @NotNull MCPlayer getByPlatform(@NotNull Object platformPlayer);

    @Nullable MCPlayer findByPlatform(@NotNull Object platformPlayer);

    @ApiStatus.Internal
    void addPlayer(@NotNull MCPlayer player);

    @ApiStatus.Internal
    @Nullable MCPlayer removePlayer(@NotNull UUID uuid);

    Collection<MCPlayer> getAllPlayers();

}
