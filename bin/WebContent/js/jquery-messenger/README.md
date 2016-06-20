# jQuery Messenger

jQuery Messenger is a powerful message plugin for developers to show up messages at front-end. It's easy to use. The main features contains:
- Show basic info, error, warning, success text messages or HTML/jQuery object message.
- Show desktop notification message if when current page is inactive.
- Append a message to a specified DOM node.
- Show message at top-left/center/right or bottom-left/center/right.
- Manage and analyze messages.
- Upload messages to server.

## Browser Support 
- IE10+

## Dependence
- jQuery 1.10+

## Installation
_The path is depend on the project._

    <script type="text/javascript" src="../lib/jquery-1.11.1.js"></script>
    <link rel="stylesheet" href="../style/jquery-messenger.css">
    <script type="text/javascript" src="../jquery-messenger.js"></script>
    <!-- lang file is optional, default language is English -->
    <script type="text/javascript" src="../lang/zh-CN.js"></script>

## Usage
Reference to demo.


## API Documentation

### Messenger.info(messages, [opt])
- @param {string|jQuery|Array} messages
- @param {object} [opt]
- @returns {Messenger}

Show 'info' messages with a blue box.


```
// Show a single string message.
Messenger.info('This is a common message alert.');
```

```
// Show a message array at one time.
Messenger.info(['Message 1st.', 'Message 2nd.', 'Message 3rd.']);
```

```
// Show jQuery object with a event button.
var btn = $('#btn').click(function(){ alert('event works.'); });
Messenger.info(btn);
```

```
// Show a message with optional parameters.
Messenger.info('Hello World!', {
	target: $('#message-wrapper'),
	closable: false,
	messageIcon: true,
	messageTime: true
});
```

### Messenger.success(messages, [opt])
Show 'success' messages with a green box. The usage description is the same as Messenger.info(messages, [opt]) described.

### Messenger.warning(messages, [opt])
Show 'warning' messages with a yellow box. The usage description is the same as Messenger.info(messages, [opt]) described.

### Messenger.error(messages, [opt])
Show 'error' messages with a red box. The usage description is the same as Messenger.info(messages, [opt]) described.


### Messenger.clear()
-@returns {Messenger}

Clear all popup messages.

### Messenger.remove()
-@returns {Messenger}

Remove all of the cached data.


### Messenger.openConsole()
-@returns {Messenger}

Show management dialog.


### Messenger.closeConsole()
-@returns {Messenger}

Close management dialog.


### Messenger.setting(opts)
-@returns {Messenger}

Global plugin settings, this will override default settings.

```
var settings = {
            /**
             * true - show desktop notification.
             */
            notify: true,
            /**
             * desktop notification message icon prefix-url.
             */
            notifyIconUrl: '..',
            /**
             * true - show message close button.
             */
            closable: true,
            /**
             * true - show messenger open button at the bottom of the box when message alert.
             */
            handle: true,
            /**
             * true - each message item will append to previous one before timeout.
             * false - there will be only one message item at one time.
             */
            multiple: true,
            /**
             * Popup message max size for show up at one time, and it works if 'multiple' = true.
             */
            popupSize: 5,
            /**
             * Messenger box layout, contains: top-left, top-center, top-right, bottom-left, bottom-center, bottom-right.
             */
            layout: 'top-center',
            /**
             * The parent element object for messenger box.
             */
            target: 'body',
            /**
             * The width of the messenger box. Full width is '100%'.
             */
            width: 800,
            /**
             * The milliseconds for message closing. Default '0' timeout is calculate by text length.
             */
            timeout: 0,
            /**
             * Maximum caching for message items.
             */
            cacheSize: 1000,
            /**
             * Message page size in management console.
             */
            pageSize: 5,
            /**
             * true - Upload message item automatically.
             */
            autoUpload: false,
            /**
             * Message upload url. It works if 'autoUpload' = true.
             */
            uploadUrl: 'messenger_log',
            /**
             * The message type that set to 'true' will be uploaded if 'autoUpload' = true.
             */
            uploadType: {
                info: true,
                success: true,
                warning: true,
                error: true
            },
            /**
             * Show message time for each item.
             */
            showTime: true,
            /**
             * Show message icon for each item.
             */
            showIcon: true,
            /**
             * Callback function after message created.
             * @param {object} message
             */
            onCreated: function (message) {
            },
            /**
             * Callback function after message uploaded to server.
             * @param {object} message
             * @param {object} data json object form server side.
             */
            onUploaded: function (message, data) {
            },
            /**
             * Callback function after close a message item, from DOM or desktop notification.
             * @param {object} message
             * @param {object} event
             */
            onClosed: function (message, event) {
            }
        }
```

### Messenger.getSettings()
-@returns {objects} settings

Get global plugin settings.


### Messenger.getMessage(mid)
-@param {string} mid
-@returns {*|{}}

Get single message by message id.


### Messenger.getMessages()
-@returns {{}}

Get all of the message cache.


### Messenger.getMessagesId()
-@returns {Array}

Get all of the message id cache.


### Messenger.setNotify(enable)
-@param {boolean} enable true - Show desktop notification when current page is inactive.
-@returns {Messenger}

Set desktop notification switch. 


### Messenger.setNotifyIconUrl()
-@param {string} url
-@returns {Messenger}

Set the prefix uri for desktop notification icons.


### Messenger.setClosable(enable)
-@param {boolean} enable true - Show message closable button switch(At the top right of the message).
-@returns {Messenger}

Set message closable button switch.


### Messenger.setCacheSize()
-@param {number} size
-@returns {Messenger}

Set message cache size.


### Messenger.setMultiple(enable)
-@param {boolean} enable true - Show messages(popupSize) at one time. If the number of the message reach popupSize, the last one will be replaced by the new one. All messages will be disappeared when time out. false - Show a single message at one time.
-@returns {Messenger}

Set message popup mode.


### Messenger.setPopupSize(size)
-@param {number} size
-@returns {Messenger}

The maximum message number in the popup message box.


### Messenger.setPageSize(size)
-@param {number} size
-@returns {Messenger}

The maximum message number in the messenger console box.


### Messenger.setLayout(layout)
-@param {string} layout
-@returns {Messenger}

Set UI layout, top-center/top-left/top-right/bottom-center/bottom-left/bottom-right.


### Messenger.setTarget(target)
-@param {string|jQuery|$} target
-@returns {Messenger}

Set messenger box append target.


### Messenger.setTargetDefault()
-@returns {Messenger}

Set messenger default append target 'body'.


### Messenger.setWidth(width)
-@param {number|string} width Number or percentage string will be ok.
-@returns {Messenger}

Set the width of the message box.


### Messenger.setTimeout(timeout)
-@param {number} timeout 0 - Auto calculated by system;
-@returns {Messenger}

Set message timeout. This will be override by single message definition.


### Messenger.setAutoUpload(enable)
-@param {boolean} enable
-@returns {Messenger}

Auto upload message to server after created.


### Messenger.setUploadUrl(url)
-@param {string} url
-@returns {Messenger}

Messenger auto upload url.


### Messenger.setUploadType(typeObj)
-@param {object} typeObj {info:true, success: true, warning: true, error: true}
-@returns {Messenger}

Set upload message type that need to.


### Messenger.setHandle(enable)
-@param {boolean} enable
-@returns {Messenger}

Set show/hide messenger handle bar switch when mouse hover on.


### Messenger.setMessageTime(enable)
-@param {boolean} enable
-@returns {Messenger}

Set show/hide message time when message created.


### Messenger.setMessageIcon(enable)
-@param {boolean} enable
-@returns {Messenger}

Set show/hide message icon when message created.
fonts