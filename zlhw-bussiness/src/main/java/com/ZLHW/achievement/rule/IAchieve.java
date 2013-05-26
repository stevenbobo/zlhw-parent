package com.ZLHW.achievement.rule;

import com.ZLHW.achievement.model.AchievementInfo;
import com.ZLHW.achievement.model.AtomicTask;
import com.ZLHW.achievement.model.SinaUser;

public interface IAchieve {
	public boolean validate(SinaUser user,AtomicTask task,AchievementInfo achievementInfo);
}

