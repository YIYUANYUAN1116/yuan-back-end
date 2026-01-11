package com.yuan.workflow.core.evaluator;

import com.yuan.workflow.model.Expression;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConditionGroup {

    public enum Relation {
        AND, OR
    }

    private Relation relation = Relation.AND;

    private List<Expression> expressions = new ArrayList<>();

    private List<ConditionGroup> groups = new ArrayList<>();
}