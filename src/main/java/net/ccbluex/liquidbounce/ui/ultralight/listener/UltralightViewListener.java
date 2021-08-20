/*
 * Ultralight Java - Java wrapper for the Ultralight web engine
 * Copyright (C) 2020 - 2021 LabyMedia and contributors
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package net.ccbluex.liquidbounce.ui.ultralight.listener;

import com.labymedia.ultralight.UltralightView;
import com.labymedia.ultralight.input.UltralightCursor;
import com.labymedia.ultralight.math.IntRect;
import com.labymedia.ultralight.plugin.view.MessageLevel;
import com.labymedia.ultralight.plugin.view.MessageSource;

/**
 * Example view listener for the main view.
 * <p>
 * Instances of view listeners receive view bound events and can be used to update visual aspects such as the cursor and
 * the tooltip.
 * <p>
 * A view listener is registered using {@link UltralightView#setViewListener(com.labymedia.ultralight.plugin.view.UltralightViewListener)}.
 */
public class UltralightViewListener implements com.labymedia.ultralight.plugin.view.UltralightViewListener {
    /**
     * Constructs a new {@link UltralightViewListener} and used the given cursor manager for changing the displayed
     * cursor.
     */
    public UltralightViewListener() {
    }

    /**
     * Called by Ultralight when the page title changes.
     *
     * @param title The new page title
     */
    @Override
    public void onChangeTitle(String title) {
        System.out.println("View title has changed: " + title);
    }

    /**
     * Called by Ultralight when the view URL changes.
     *
     * @param url The new page url
     */
    @Override
    public void onChangeURL(String url) {
        System.out.println("View url has changed: " + url);
    }

    /**
     * Called by Ultralight when the displayed tooltip changes.
     *
     * @param tooltip The new page tooltip
     */
    @Override
    public void onChangeTooltip(String tooltip) {
        // System.out.println("View tooltip has changed: " + tooltip);
    }

    /**
     * Called by Ultralight when the cursor changes. Ultralight supports a lot of cursors, but currently not a custom
     * one.
     *
     * LWJGL2 not supported to change cursor
     *
     * @param cursor The new page cursor
     */
    @Override
    public void onChangeCursor(UltralightCursor cursor) {
        System.out.println("Cursor has changed: " + cursor);
    }

    /**
     * Called when a message is added to the console. This includes, but is not limited to, {@code console.log} and
     * friends.
     *
     * @param source       The source the message originated from
     * @param level        The severity of the message
     * @param message      The message itself
     * @param lineNumber   The line the message originated from
     * @param columnNumber The column the message originated from
     * @param sourceId     The id of the source
     */
    @Override
    public void onAddConsoleMessage(
            MessageSource source,
            MessageLevel level,
            String message,
            long lineNumber,
            long columnNumber,
            String sourceId
    ) {
        System.out.println(
                "View message: ["
                        + source.name() + "/" + level.name() + "] "
                        + sourceId + ":" + lineNumber + ":" + columnNumber + ": " + message);
    }

    /**
     * Called by Ultralight when a new view is requested. This is your chance to either open the view in an external
     * browser, in a new application internal tab, or, if desired, to just ignore it entirely.
     *
     * @param openerUrl The URL of the page that initiated this request
     * @param targetUrl The URL that the new View will navigate to
     * @param isPopup   Whether or not this was triggered by window.open()
     * @param popupRect Popups can optionally request certain dimensions and coordinates via window.open(). You can
     *                  choose to respect these or not by resizing/moving the View to this rect.
     * @return The view to display the new URL in, or {@code null}, if the request should not be further handled by
     * Ultralight
     */
    @Override
    public UltralightView onCreateChildView(String openerUrl, String targetUrl, boolean isPopup, IntRect popupRect) {
        System.out.println("View wants child: ");
        System.out.println("\tFrom: " + openerUrl);
        System.out.println("\tTo: " + targetUrl);
        System.out.println("\tIs popup: " + isPopup);
        if (popupRect.isValid()) {
            System.out.println("\tTarget rect: (" +
                    popupRect.x() + "," + popupRect.y() + " -> " + popupRect.width() + "," + popupRect.height() + ")");
        } else {
            System.out.println("\tTarget rect: NONE");
        }
        System.out.println("Cancelling request, multi view not implemented");

        // Returning null will stop Ultralight from further handling the request, ignoring it altogether
        return null;
    }
}
