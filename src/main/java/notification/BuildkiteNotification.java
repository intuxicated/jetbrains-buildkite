package notification;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;

public class BuildkiteNotification {

    static NotificationGroup notificationGroup() {
        return new NotificationGroup("Buildkite Notification", NotificationDisplayType.BALLOON, true);
    }

    public static Notification info(String title, String message) {
        return notificationGroup().createNotification(title, message, NotificationType.INFORMATION, null);
    }

}
