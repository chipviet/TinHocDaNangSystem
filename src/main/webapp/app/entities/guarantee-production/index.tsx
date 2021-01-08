import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GuaranteeProduction from './guarantee-production';
import GuaranteeProductionDetail from './guarantee-production-detail';
import GuaranteeProductionUpdate from './guarantee-production-update';
import GuaranteeProductionDeleteDialog from './guarantee-production-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GuaranteeProductionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GuaranteeProductionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GuaranteeProductionDetail} />
      <ErrorBoundaryRoute path={match.url} component={GuaranteeProduction} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GuaranteeProductionDeleteDialog} />
  </>
);

export default Routes;
