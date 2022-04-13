package io.helidon.mapstruct.mp;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface SourceTargetMapper {

    Target toTarget(Source source);

    interface _sourceDelegate {
        @Decorator
        abstract class SourceTargetMapperDecorator implements SourceTargetMapper {

            @Inject
            @Delegate
            private SourceTargetMapper delegate;

            @Override
            public Target toTarget(Source source) {
                System.err.println("___Decorating___ enclosing type");

                Target t = delegate.toTarget(source);
                t.setName("I got decorated");

                return t;
            }
        }
    }
}
