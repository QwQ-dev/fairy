/*
 * MIT License
 *
 * Copyright (c) 2021 Imanity
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

package org.fairy.bukkit.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.fairy.Fairy;
import org.fairy.bean.Autowired;
import org.fairy.bean.BeanHolder;
import org.fairy.bukkit.scoreboard.SidebarService;
import org.fairy.bukkit.util.TaskUtil;
import org.fairy.bukkit.Imanity;
import org.fairy.bukkit.events.player.PlayerPostJoinEvent;
import org.fairy.bukkit.listener.events.Events;
import org.fairy.bukkit.metadata.Metadata;
import org.fairy.bean.Component;
import org.fairy.bukkit.timer.TimerList;
import org.fairy.bukkit.timer.impl.PlayerTimer;
import org.fairy.metadata.MetadataKey;
import org.fairy.metadata.MetadataMap;

@Component
public class PlayerListener implements Listener {

    @Autowired
    public BeanHolder<SidebarService> sidebarService;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoinScoreboard(PlayerJoinEvent event) {
        if (!Fairy.isRunning()) {
            return;
        }

        Player player = event.getPlayer();

        this.sidebarService.supplyOrNull(service -> service.getOrCreateScoreboard(player));

        if (Imanity.TAB_HANDLER != null) {
            Imanity.TAB_HANDLER.registerPlayerTablist(player);
        }

        TaskUtil.runScheduled(() -> Imanity.callEvent(new PlayerPostJoinEvent(player)), 1L);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (!Fairy.isRunning()) {
            return;
        }

        Player player = event.getPlayer();

        this.sidebarService.runOrNull(service -> service.remove(player));

        if (Imanity.TAB_HANDLER != null) {
            Imanity.TAB_HANDLER.removePlayerTablist(player);
        }

        Events.unregisterAll(player);
        MetadataMap metadataMap = Metadata.provideForPlayer(player);
        metadataMap.ifPresent(PlayerTimer.TIMER_METADATA_KEY, TimerList::clear);
        for (MetadataKey<?> key : metadataMap.asMap().keySet()) {
            if (key.removeOnNonExists()) {
                metadataMap.remove(key);
            }
        }
    }

}